package cz.cvut.fel.annotator.service;

import cz.cvut.fel.annotator.dto.annotation.AnnotationDto;
import cz.cvut.fel.annotator.dto.annotation.mapper.AnnotationMapper;
import cz.cvut.fel.annotator.repository.AnnotationDao;
import cz.cvut.fel.annotator.repository.model.Annotation;
import cz.cvut.fel.annotator.repository.model.MediaAsset;
import cz.cvut.fel.annotator.shared.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnotationService {

    private final AnnotationDao annotationDao;
    private final AnnotationMapper annotationMapper;

    public List<AnnotationDto> getByMediaAssetReferenceId(String referenceId) {
        Objects.requireNonNull(referenceId, Constants.Validation.REFERENCE_ID);

        log.debug("{} Loading annotations for referenceId={}",
                Constants.Log.ANNOTATION_SERVICE, referenceId);

        List<Annotation> annotations =
                annotationDao.findByMediaAssetReferenceId(referenceId);

        log.info("{} Found {} annotation(s) for referenceId={}",
                Constants.Log.ANNOTATION_SERVICE, annotations.size(), referenceId);

        return annotations.stream()
                .map(annotationMapper::toDto)
                .toList();
    }

    /**
     * Annotation counts for all annotated media assets, keyed by reference id.
     * Single batched query — use this when listing playlists instead of calling
     * {@link #countByMediaAssetReferenceId(String)} per item.
     */
    public Map<String, Integer> countByMediaAssetReferenceId() {
        Map<String, Integer> counts = annotationDao.countAnnotationsByMediaReferenceId();

        log.info("{} Loaded annotation counts for {} annotated media asset(s)",
                Constants.Log.ANNOTATION_SERVICE, counts.size());

        return counts;
    }

    public int countByMediaAssetReferenceId(String referenceId) {
        Objects.requireNonNull(referenceId, Constants.Validation.REFERENCE_ID);

        int count = annotationDao.findByMediaAssetReferenceId(referenceId).size();

        log.debug("{} Annotation count={} for referenceId={}",
                Constants.Log.ANNOTATION_SERVICE, count, referenceId);

        return count;
    }

    @Transactional
    public void replaceAnnotations(MediaAsset mediaAsset, List<AnnotationDto> dtos) {
        Objects.requireNonNull(mediaAsset, Constants.Validation.MEDIA_ASSET);
        Objects.requireNonNull(dtos, Constants.Validation.DTOS);

        int existingCount = mediaAsset.getAnnotations() != null
                ? mediaAsset.getAnnotations().size()
                : 0;

        log.info("{} Replacing annotations on referenceId={} removing={} adding={}",
                Constants.Log.ANNOTATION_SERVICE,
                mediaAsset.getReferenceId(),
                existingCount,
                dtos.size());

        removeAllAnnotations(mediaAsset);

        List<Annotation> newEntities = dtos.stream()
                .map(annotationMapper::toEntity)
                .toList();

        newEntities.forEach(annotationDao::persist);
        mediaAsset.getAnnotations().addAll(newEntities);

        log.debug("{} Replacement complete for referenceId={}",
                Constants.Log.ANNOTATION_SERVICE,
                mediaAsset.getReferenceId());
    }

    private void removeAllAnnotations(MediaAsset mediaAsset) {
        if (mediaAsset.getAnnotations() == null) {
            mediaAsset.setAnnotations(new HashSet<>());
            return;
        }

        log.debug("{} Removing {} existing annotation(s) from referenceId={}",
                Constants.Log.ANNOTATION_SERVICE,
                mediaAsset.getAnnotations().size(),
                mediaAsset.getReferenceId());

        mediaAsset.getAnnotations().forEach(annotationDao::remove);
        mediaAsset.getAnnotations().clear();
    }
}