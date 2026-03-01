package cz.cvut.fel.annotator.annotation.service;

import cz.cvut.fel.annotator.annotation.dto.AnnotationDto;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAsset;
import cz.cvut.fel.annotator.mediaAsset.service.MediaAssetFacade;
import cz.cvut.fel.annotator.mediaAsset.service.MediaAssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaAnnotationFacade {

    private final MediaAssetFacade mediaAssetService;
    private final AnnotationService annotationService;

    public void patch(String referenceId, List<AnnotationDto> dtos) {
        log.info("Patching media asset {} with annotations count = {}", referenceId, dtos.size());
        MediaAsset asset = mediaAssetService.getEntityByIdOrPersist(referenceId);
        annotationService.patchMediaAsset(asset, dtos);
    }
}
