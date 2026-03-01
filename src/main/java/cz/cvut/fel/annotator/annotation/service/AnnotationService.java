package cz.cvut.fel.annotator.annotation.service;

import cz.cvut.fel.annotator.annotation.dto.AnnotationDto;
import cz.cvut.fel.annotator.annotation.dto.mapper.AnnotationMapper;
import cz.cvut.fel.annotator.annotation.persistence.Annotation;
import cz.cvut.fel.annotator.annotation.persistence.AnnotationDao;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAsset;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAssetDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnotationService {

    private final AnnotationDao annotationDao;
    private final MediaAssetDao mediaAssetDao;
    private final AnnotationMapper annotationMapper;

    public List<AnnotationDto> getByMediaAssetReferenceId(String referenceId) {
        log.info("Get annotations for media asset {}", referenceId);
        return annotationDao.findByMediaAssetReferenceId(referenceId)
                .stream()
                .map(annotationMapper::toDto)
                .toList();
    }

    @Transactional
    public void addAnnotationsForMediaAssetByReferenceId(
            String referenceId,
            List<AnnotationDto> annotationDtos
    ) {
        log.info("Adding {} annotations to media asset {}", annotationDtos.size(), referenceId);

        MediaAsset mediaAsset = mediaAssetDao.getByReferenceId(referenceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Media asset not found for referenceId: " + referenceId));

        for (AnnotationDto dto : annotationDtos) {
            Annotation annotation = annotationMapper.toEntity(dto);
            annotationDao.persist(annotation);
            mediaAsset.getAnnotations().add(annotation);
        }

        log.debug("Successfully added annotations to media asset {}", referenceId);
    }

    public void patchMediaAsset(
            MediaAsset mediaAsset,
            List<AnnotationDto> annotationDtos
    ) {

        if (mediaAsset.getAnnotations() != null) {
            for (Annotation annotation : mediaAsset.getAnnotations()) {
                annotationDao.remove(annotation);
            }
            mediaAsset.getAnnotations().clear();
        } else {
            mediaAsset.setAnnotations(new HashSet<>());
        }


        for (AnnotationDto dto : annotationDtos) {
            Annotation entity = annotationMapper.toEntity(dto);
            annotationDao.persist(entity);
            mediaAsset.getAnnotations().add(entity);
        }

        mediaAsset.setModifiedAt(LocalDateTime.now(ZoneOffset.UTC));
        mediaAssetDao.update(mediaAsset);
        log.info("Patched annotations for media asset {}", mediaAsset.getReferenceId());
    }


    public Integer getAnnotationsCountByMediaAssetReferenceId(String referenceId) {
        log.info("Getting annotations count for media asset {}", referenceId);
        return annotationDao
                .findByMediaAssetReferenceId(referenceId)
                .size();
    }


    @Transactional
    public void deleteAnnotationsForMediaAssetByReferenceId(
            String referenceId,
            String annotationId
    ) {
        log.info("Deleting annotation {} from media asset {}", annotationId, referenceId);

        MediaAsset mediaAsset = mediaAssetDao.getByReferenceId(referenceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Media asset not found for referenceId: " + referenceId));

        Annotation annotation = annotationDao.findById(URI.create(annotationId))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Annotation not found: " + annotationId));

        mediaAsset.getAnnotations().remove(annotation);
        annotationDao.remove(annotation);

        log.debug("Successfully deleted annotation {} from media asset {}", annotationId, referenceId);
    }


}
