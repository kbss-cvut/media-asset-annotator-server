package cz.cvut.fel.annotator.controller;

import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDto;
import cz.cvut.fel.annotator.service.MediaAssetFacadeService;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class MediaAssetController {

    private final WebUtils webUtils;
    private final MediaAssetFacadeService mediaAssetService;


    @GetMapping("/adapter/playlist/{playlistId}")
    public ResponseEntity<List<MediaAssetDto>> getPlaylist(
            @PathVariable String playlistId) {

        webUtils.validateParams(playlistId);

        log.debug("{} GET playlistId={}",
                Constants.Log.MEDIA_ASSET_CONTROLLER, playlistId);

        return ResponseEntity.ok(
                mediaAssetService.findPlaylist(playlistId)
        );
    }


    @GetMapping("/{referenceId}")
    public ResponseEntity<MediaAssetDto> getByReferenceId(
            @PathVariable String referenceId) {

        webUtils.validateParams(referenceId);

        log.debug("{} GET referenceId={}",
                Constants.Log.MEDIA_ASSET_CONTROLLER, referenceId);

        return ResponseEntity.ok(
                mediaAssetService.findMediaByReferenceId(referenceId)
        );
    }

    @GetMapping("/by-source")
    public ResponseEntity<MediaAssetDto> getBySource(
            @RequestParam String source) {

        webUtils.validateParams(source);

        log.debug("{} GET source={}",
                Constants.Log.MEDIA_ASSET_CONTROLLER, source);

        return ResponseEntity.ok(
                mediaAssetService.findMediaBySource(source)
        );
    }

}