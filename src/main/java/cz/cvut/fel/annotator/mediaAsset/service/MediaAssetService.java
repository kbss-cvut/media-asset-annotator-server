package cz.cvut.fel.annotator.mediaAsset.service;

import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import cz.cvut.fel.annotator.mediaAsset.dto.mapper.MediaAssetMapper;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAsset;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAssetDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaAssetService {

    private final MediaAssetDao dao;
    private final MediaAssetMapper mediaAssetMapper;

    public Optional<MediaAssetDto> findById(String token) {
        log.debug("Fetching MediaAsset {} from local DB", token);
        return dao
                .getByReferenceId(token)
                .map(mediaAssetMapper::toDto);
    }
}