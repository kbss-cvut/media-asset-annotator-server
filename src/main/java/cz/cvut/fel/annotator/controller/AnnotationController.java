package cz.cvut.fel.annotator.controller;

import cz.cvut.fel.annotator.dto.annotation.AnnotationDto;
import cz.cvut.fel.annotator.service.MediaAssetFacadeService;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/annotation")
@RequiredArgsConstructor
public class AnnotationController {

    private final MediaAssetFacadeService service;
    private final WebUtils webUtils;


    @GetMapping(
            path = "/media/{referenceId}",
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<AnnotationDto>> getAnnotations(
            @PathVariable String referenceId) {

        webUtils.validateParams(referenceId);

        log.debug("{} GET referenceId={}",
                Constants.Log.ANNOTATION_CONTROLLER, referenceId);

        return ResponseEntity.ok(service.findAnnotationsByMediaAssetReferenceId(referenceId));
    }

    @PatchMapping(
            path = "/media/{referenceId}",
            consumes = MimeTypeUtils.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> replaceAnnotations(
            @PathVariable String referenceId,
            @RequestBody List<AnnotationDto> dtos) {

        webUtils.validateParams(referenceId, dtos);

        log.info("{} PUT referenceId={} count={}",
                Constants.Log.ANNOTATION_CONTROLLER,
                referenceId,
                dtos.size());

        service.replaceAnnotationsByMediaReferenceId(referenceId, dtos);

        return ResponseEntity.ok().build();
    }


}