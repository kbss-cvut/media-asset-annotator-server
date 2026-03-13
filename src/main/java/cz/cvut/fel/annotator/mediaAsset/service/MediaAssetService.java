package cz.cvut.fel.annotator.mediaAsset.service;

import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import cz.cvut.fel.annotator.mediaAsset.dto.mapper.MediaAssetMapper;
import cz.cvut.fel.annotator.mediaAsset.persistence.MediaAssetDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaAssetService {

    private final MediaAssetDao dao;
    private final MediaAssetMapper mediaAssetMapper;

    public Optional<MediaAssetDto> findById(String token) {
        return dao
                .findByReferenceId(token)
                .map(mediaAssetMapper::toDto);
    }

    public Optional<MediaAssetDto> findBySource(String source) {
        return dao.findBySource(source)
                .map(mediaAssetMapper::toDto);
    }
}