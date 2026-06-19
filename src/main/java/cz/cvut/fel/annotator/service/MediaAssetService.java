package cz.cvut.fel.annotator.service;

import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDto;
import cz.cvut.fel.annotator.dto.mediaAsset.mapper.MediaAssetMapper;
import cz.cvut.fel.annotator.dto.mediaAsset.mapper.MediaAssetMapperUtils;
import cz.cvut.fel.annotator.repository.MediaAssetDao;
import cz.cvut.fel.annotator.repository.model.MediaAsset;
import cz.cvut.fel.annotator.shared.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaAssetService {

    private final MediaAssetDao dao;
    private final MediaAssetMapper mapper;


    public Optional<MediaAssetDto> findBySource(String source) {
        Objects.requireNonNull(source, Constants.Validation.SOURCE);

        log.debug("{} Local DB lookup source={}",
                Constants.Log.MEDIA_ASSET_SERVICE, source);

        Optional<MediaAssetDto> result = dao.findBySource(source)
                .map(mapper::toDto);

        log.info("{} Local lookup source={} found={}",
                Constants.Log.MEDIA_ASSET_SERVICE,
                source,
                result.isPresent());

        return result;
    }

    public Optional<MediaAsset> findEntityByReferenceId(String referenceId) {
        Objects.requireNonNull(referenceId, Constants.Validation.REFERENCE_ID);

        log.debug("{} Local entity lookup referenceId={}",
                Constants.Log.MEDIA_ASSET_SERVICE, referenceId);

        return dao.findByReferenceId(referenceId);
    }

    /**
     * Maps reference id → annotation-modified timestamp (ISO-8601) for every
     * persisted asset. Only annotated assets are persisted, so this is a single
     * small query and avoids a per-item lookup when listing a playlist.
     * {@code modifiedAt}/{@code referenceId} are eagerly fetched, so the lazy
     * {@code annotations} collection is never touched.
     */
    public Map<String, String> annotationsModifiedAtByReferenceId() {
        Map<String, String> result = new HashMap<>();
        for (MediaAsset asset : dao.findAll()) {
            if (asset.getReferenceId() == null || asset.getModifiedAt() == null) {
                continue;
            }
            result.put(asset.getReferenceId(), MediaAssetMapperUtils.mapModifiedAt(asset));
        }

        log.info("{} Loaded annotationsModifiedAt for {} persisted asset(s)",
                Constants.Log.MEDIA_ASSET_SERVICE, result.size());

        return result;
    }

    public MediaAsset persistAndReturn(MediaAsset entity) {
        Objects.requireNonNull(entity, Constants.Validation.MEDIA_ASSET);

        log.info("{} Persisting new MediaAsset referenceId={}",
                Constants.Log.MEDIA_ASSET_SERVICE,
                entity.getReferenceId());

        dao.persist(entity);

        return entity;
    }
}