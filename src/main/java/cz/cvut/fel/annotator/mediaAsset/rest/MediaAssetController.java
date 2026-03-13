package cz.cvut.fel.annotator.mediaAsset.rest;

import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import cz.cvut.fel.annotator.mediaAsset.service.MediaAssetFacade;
import cz.cvut.fel.annotator.mediaAsset.service.MediaAssetService;
import cz.cvut.fel.annotator.mediaAsset.service.MediaCmsAdapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/asset")
@RequiredArgsConstructor
public class MediaAssetController {

    private final MediaAssetFacade mediaAssetFacade;

    @GetMapping("/adapter/playlist/{listId}")
    public ResponseEntity<List<MediaAssetDto>> getMediaAssetsFromMediaCms(
            @PathVariable String listId
    ) {
        log.debug("GET playlist id with adapter asset {}", listId);
        return ResponseEntity.ok(
                mediaAssetFacade.getPlaylist(listId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaAssetDto> getMediaAssetById(@PathVariable String id) {
        log.debug("GET media asset by id{}", id);
        MediaAssetDto asset = mediaAssetFacade.getById(id);
        return ResponseEntity.ok(asset);
    }


    @GetMapping("/source")
    public ResponseEntity<MediaAssetDto> getMediaAssetBySource(@RequestParam String url) {
        log.debug("GET media asset by source {}", url);
        MediaAssetDto asset = mediaAssetFacade.getBySource(url);
        return ResponseEntity.ok(asset);
    }



}
