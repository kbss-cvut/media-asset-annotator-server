package cz.cvut.fel.annotator.service;

import cz.cvut.fel.annotator.client.mediaCms.MediaCmsUrlResolver;
import cz.cvut.fel.annotator.client.recordManager.RecordManagerClient;
import cz.cvut.fel.annotator.dto.annotation.AnnotationDtoLD;
import cz.cvut.fel.annotator.dto.annotation.mapper.AnnotationMapper;
import cz.cvut.fel.annotator.dto.mediaAsset.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDtoLD;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaContext;
import cz.cvut.fel.annotator.dto.mediaAsset.mapper.MediaAssetMapper;
import cz.cvut.fel.annotator.repository.model.MediaAsset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplaceNotifierService {

    private final RecordManagerClient recordManagerClient;
    private final MediaCmsUrlResolver urlResolver;
    private final MediaAssetMapper mediaAssetMapper;
    private final AnnotationMapper annotationMapper;

    public void notify(MediaContext context) {
        if (context == null || context.entity() == null) {
            log.warn("Skipping notify: context or entity is null");
            return;
        }

        MediaAsset entity = context.entity();
        String referenceId = entity.getReferenceId();

        log.debug("Notify RecordManager for referenceId={}", referenceId);

        try {
            String src = resolveHLSSource(context.cms(), entity);
            MediaAssetDtoLD payload = mediaAssetMapper.toLd(entity.getSource(), entity, src);
            List<AnnotationDtoLD> annotations = entity.getAnnotations().stream()
                    .map(annotationMapper::toLd)
                    .toList();
            payload.setHasAnnotation(annotations);
            recordManagerClient.putMediaAsset(payload);


        } catch (Exception e) {
            log.warn("Failed to notify RecordManager for referenceId={} msg={}", referenceId, e.getMessage());
        }
    }

    /**
     * Prefer HLS → fallback to original → fallback to entity source
     */
    private String resolveHLSSource(MediaCmsMediaDto cms, MediaAsset entity) {
        if (cms != null) {
            if (cms.hlsInfo() != null && cms.hlsInfo().masterFile() != null) {
                return urlResolver.resolveMediaUrl(cms.hlsInfo().masterFile());
            }

            if (cms.originalMediaUrl() != null) {
                return urlResolver.resolveMediaUrl(cms.originalMediaUrl());
            }
        }

        // fallback
        return entity.getSource();
    }
}