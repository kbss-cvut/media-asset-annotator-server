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
import java.util.Map;
import java.util.Objects;
import java.util.function.IntSupplier;

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

            String thumbnailUrl = urlResolver.resolveMediaUrl(
                    media.thumbnailUrl()
            );

            return mapper.fromMedia(
                    id,
                    media,
                    src,
                    thumbnailUrl,
                    annotationCount.getAsInt()
            );

        } catch (MediaCmsException ex) {
            throw resolve(ex, Constants.MediaCms.CATEGORY_MEDIA + id);
        }
    }


    public List<MediaAssetDto> getPlaylist(
            String playlistId,
            Map<String, Integer> annotationCounts,
            Map<String, String> annotationsModifiedAt) {

        Objects.requireNonNull(playlistId, Constants.Validation.ID);
        Objects.requireNonNull(annotationCounts, "annotationCounts must not be null");
        Objects.requireNonNull(annotationsModifiedAt, "annotationsModifiedAt must not be null");

        log.debug("{} GET playlist id={}",
                Constants.Log.MEDIA_CMS_ADAPTER, playlistId);

        PlaylistResponseDto playlist = fetchPlaylist(playlistId);

        if (isEmpty(playlist)) {
            log.info("{} Playlist id={} is empty",
                    Constants.Log.MEDIA_CMS_ADAPTER, playlistId);
            return List.of();
        }

        List<MediaAssetDto> result = playlist.playlistMedia().stream()
                .map(media -> mapPlaylistItem(media, annotationCounts, annotationsModifiedAt))
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

    /**
     * Builds a list item entirely from the playlist listing — no per-item
     * MediaCMS detail call. {@code thumbnail_url} is already an absolute URL in
     * the listing; {@code resolveMediaUrl} leaves it untouched (and rebases it
     * onto the public base if MediaCMS ever returns a relative path).
     */
    private MediaAssetDto mapPlaylistItem(
            PlaylistMediaDto media,
            Map<String, Integer> annotationCounts,
            Map<String, String> annotationsModifiedAt) {

        String thumbnailUrl = urlResolver.resolveMediaUrl(media.thumbnailUrl());
        int count = annotationCounts.getOrDefault(media.friendlyToken(), 0);
        String annModifiedAt = annotationsModifiedAt.get(media.friendlyToken());

        return mapper.fromPlaylist(media, thumbnailUrl, annModifiedAt, count);
    }

    private boolean isEmpty(PlaylistResponseDto playlist) {
        return playlist.playlistMedia() == null || playlist.playlistMedia().isEmpty();
    }

    private RuntimeException resolve(MediaCmsException ex, String context) {
        return errorResolver.resolve(ex, context);
    }
}