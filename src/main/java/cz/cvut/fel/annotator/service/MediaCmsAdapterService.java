package cz.cvut.fel.annotator.service;

import cz.cvut.fel.annotator.client.mediaCms.MediaCmsClient;
import cz.cvut.fel.annotator.client.mediaCms.MediaCmsErrorResolver;
import cz.cvut.fel.annotator.client.mediaCms.MediaCmsUrlResolver;
import cz.cvut.fel.annotator.dto.mediaAsset.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.external.PlaylistMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.external.PlaylistResponseDto;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDto;
import cz.cvut.fel.annotator.dto.mediaAsset.mapper.MediaCmsMapper;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.exception.MediaCmsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaCmsAdapterService {

    private final MediaCmsClient client;
    private final MediaCmsErrorResolver errorResolver;
    private final MediaCmsUrlResolver urlResolver;
    private final MediaCmsMapper mapper;


    public MediaCmsMediaDto getAssetWithMediaCmsRepresentation(String referenceId) {
        Objects.requireNonNull(referenceId, Constants.Validation.REFERENCE_ID);
        log.debug("{} GET media referenceId={}",
                Constants.Log.MEDIA_CMS_ADAPTER, referenceId);

        try {
            return client.getMediaById(referenceId);
        } catch (MediaCmsException ex) {
            throw resolve(ex, Constants.MediaCms.CATEGORY_MEDIA + referenceId);
        }
    }

    public MediaAssetDto getAssetWithInternalRepresentation(String id, IntSupplier annotationCount) {
        Objects.requireNonNull(id, Constants.Validation.ID);

        log.debug("{} GET media id={}",
                Constants.Log.MEDIA_CMS_ADAPTER, id);

        try {
            MediaCmsMediaDto media = getAssetWithMediaCmsRepresentation(id);

            String src = urlResolver.resolveMediaUrl(
                    media.originalMediaUrl()
            );

            return mapper.fromMedia(
                    id,
                    media,
                    src,
                    annotationCount.getAsInt()
            );

        } catch (MediaCmsException ex) {
            throw resolve(ex, Constants.MediaCms.CATEGORY_MEDIA + id);
        }
    }


    public List<MediaAssetDto> getPlaylist(
            String playlistId,
            ToIntFunction<String> annotationCountFor) {

        Objects.requireNonNull(playlistId, Constants.Validation.ID);

        log.debug("{} GET playlist id={}",
                Constants.Log.MEDIA_CMS_ADAPTER, playlistId);

        PlaylistResponseDto playlist = fetchPlaylist(playlistId);

        if (isEmpty(playlist)) {
            log.info("{} Playlist id={} is empty",
                    Constants.Log.MEDIA_CMS_ADAPTER, playlistId);
            return List.of();
        }

        List<MediaAssetDto> result = playlist.playlistMedia().stream()
                .map(media -> mapPlaylistItem(media, annotationCountFor))
                .toList();

        log.info("{} Playlist id={} returned {} asset(s)",
                Constants.Log.MEDIA_CMS_ADAPTER, playlistId, result.size());

        return result;
    }

    private PlaylistResponseDto fetchPlaylist(String playlistId) {
        try {
            return client.getPlaylist(playlistId);
        } catch (MediaCmsException ex) {
            throw resolve(ex, Constants.MediaCms.CATEGORY_PLAYLISTS + playlistId);
        }
    }

    private MediaAssetDto mapPlaylistItem(
            PlaylistMediaDto media,
            ToIntFunction<String> annotationCountFor) {

        try {
            MediaCmsMediaDto mediaDto = client.getMediaByUrl(media.apiUrl());

            String src = urlResolver.resolveMediaUrl(
                    mediaDto.originalMediaUrl()
            );

            int count = annotationCountFor.applyAsInt(media.friendlyToken());

            return mapper.fromPlaylist(
                    media,
                    mediaDto,
                    src,
                    count
            );

        } catch (MediaCmsException ex) {
            throw resolve(ex, Constants.MediaCms.CATEGORY_MEDIA + media.apiUrl());
        }
    }

    private boolean isEmpty(PlaylistResponseDto playlist) {
        return playlist.playlistMedia() == null || playlist.playlistMedia().isEmpty();
    }

    private RuntimeException resolve(MediaCmsException ex, String context) {
        return errorResolver.resolve(ex, context);
    }
}