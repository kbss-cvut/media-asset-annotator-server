package cz.cvut.fel.annotator.service;

import cz.cvut.fel.annotator.client.mediaCms.MediaCmsUrlResolver;
import cz.cvut.fel.annotator.dto.annotation.AnnotationDto;
import cz.cvut.fel.annotator.dto.mediaAsset.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDto;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaContext;
import cz.cvut.fel.annotator.dto.mediaAsset.mapper.MediaAssetMapper;
import cz.cvut.fel.annotator.dto.mediaAsset.mapper.MediaCmsMapper;
import cz.cvut.fel.annotator.repository.model.MediaAsset;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaAssetFacadeService {

    private final MediaAssetService mediaAssetService;
    private final AnnotationService annotationService;
    private final MediaCmsAdapterService mediaCmsAdapterService;
    private final MediaAssetMapper mediaAssetMapper;
    private final MediaCmsMapper mediaCmsMapper;
    private final MediaCmsUrlResolver urlResolver;
    private final ReplaceNotifierService replaceNotifierService;

    public List<AnnotationDto> findAnnotationsByMediaAssetReferenceId(String referenceId) {
        Objects.requireNonNull(referenceId, Constants.Validation.REFERENCE_ID);
        return annotationService.getByMediaAssetReferenceId(referenceId);
    }

    public MediaAssetDto findMediaByReferenceId(String referenceId) {
        Objects.requireNonNull(referenceId, Constants.Validation.REFERENCE_ID);

        return mediaAssetService.findEntityByReferenceId(referenceId)
                .map(mediaAssetMapper::toDto)
                .orElseGet(() -> fetchFromCms(referenceId));
    }

    public List<MediaAssetDto> findPlaylist(String playlistId) {
        Objects.requireNonNull(playlistId, Constants.Validation.ID);

        return mediaCmsAdapterService.getPlaylist(
                playlistId,
                annotationService.countByMediaAssetReferenceId(),
                mediaAssetService.annotationsModifiedAtByReferenceId()
        );
    }

    public MediaAssetDto findMediaBySource(String source) {
        Objects.requireNonNull(source, Constants.Validation.SOURCE);

        return mediaAssetService.findBySource(source)
                .orElseThrow(() -> new EntityNotFoundException("MediaAsset", source));
    }

    @Transactional
    public void replaceAnnotationsByMediaReferenceId(
            String referenceId,
            List<AnnotationDto> dtos) {

        Objects.requireNonNull(referenceId, Constants.Validation.REFERENCE_ID);
        Objects.requireNonNull(dtos, Constants.Validation.DTOS);

        MediaContext context = resolveOrCreateForWrite(referenceId);
        MediaAsset entity = context.entity();

        annotationService.replaceAnnotations(entity, dtos);

        entity.setModifiedAt(LocalDateTime.now(ZoneOffset.UTC));
        mediaAssetService.persistAndReturn(entity);

        replaceNotifierService.notify(context);
    }

    private MediaAssetDto fetchFromCms(String referenceId) {
        return mediaCmsAdapterService.getAssetWithInternalRepresentation(
                referenceId,
                () -> annotationService.countByMediaAssetReferenceId(referenceId)
        );
    }

    private MediaContext resolveOrCreateForWrite(String referenceId) {
        MediaCmsMediaDto cms = mediaCmsAdapterService.getAssetWithMediaCmsRepresentation(referenceId);
        Optional<MediaAsset> entity = mediaAssetService.findEntityByReferenceId(referenceId);
        if (entity.isPresent()) {
            MediaAsset existing = entity.get();
            existing.setName(cms.title());
            existing.setDescription(cms.description());
            return new MediaContext(cms, existing);
        }

        MediaAssetDto entityDto = mediaCmsMapper.fromMedia(
                referenceId,
                cms,
                urlResolver.resolveMediaUrl(cms.originalMediaUrl()),
                urlResolver.resolveMediaUrl(cms.thumbnailUrl()),
                0);
        return new MediaContext(cms, mediaAssetMapper.toEntity(entityDto));

    }


}