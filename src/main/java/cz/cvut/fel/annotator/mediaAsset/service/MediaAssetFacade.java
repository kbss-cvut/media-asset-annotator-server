package cz.cvut.fel.annotator.mediaAsset.service;

import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import cz.cvut.fel.annotator.mediaAsset.dto.mapper.MediaAssetMapper;
import cz.cvut.fel.annotator.mediaAsset.dto.mapper.MediaAssetMapperUtils;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAsset;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAssetDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaAssetFacade {

    private final MediaAssetService mediaAssetService;
    private final MediaAssetDao dao;
    private final MediaCmsAdapterService mediaCmsAdapterService;
    private final MediaAssetMapper mediaAssetMapper;

    public MediaAssetDto getById(String token) {
        log.debug("Fetching MediaAsset {}", token);
        return mediaAssetService
                .findById(token)
                .orElseGet(() -> {
                    log.info("MediaAsset {} not found locally. Fetching from MediaCMS.", token);
                    return mediaCmsAdapterService.getMediaAsset(token);
                });
    }


    public MediaAssetDto getBySource(String source) {
        log.debug("Fetching MediaAsset by source {}", source);
        return mediaAssetService
                .findBySource(source)
                .orElseGet(() -> {
                    log.info("MediaAsset with source{} not found locally", source);
                    return  null;
                });
    }

    public MediaAsset getEntityByIdOrPersist(String token) {
        log.debug("Fetching MediaAsset entity {}", token);
        return dao
                .findByReferenceId(token)
                .orElseGet(() -> {
                    log.info("MediaAsset {} not found locally. Fetching from MediaCMS.", token);
                    MediaAssetDto dto = mediaCmsAdapterService.getMediaAsset(token);
                    MediaAsset entity = mediaAssetMapper.toEntity(dto);
                    dao.persist(entity);
                    return entity;
                });
    }

    public List<MediaAssetDto> getPlaylist(String playlistId) {
        log.debug("Fetching playlist {}", playlistId);
        return mediaCmsAdapterService.getMediaAssetsList(playlistId)
                .stream()
                .map(this::enrichWithLocalData)
                .toList();
    }

    private MediaAssetDto enrichWithLocalData(MediaAssetDto dto) {
        Optional<MediaAsset> byReferenceId = dao.findByReferenceId(dto.id());
        return byReferenceId
                .map(local -> local.getModifiedAt() != null
                        ? dto.withModifiedAt(MediaAssetMapperUtils.formatInstant(
                        local.getModifiedAt().toInstant(ZoneOffset.UTC)))
                        : dto)
                .orElse(dto);
    }
}