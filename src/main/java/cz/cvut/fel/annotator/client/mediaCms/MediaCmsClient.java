package cz.cvut.fel.annotator.client.mediaCms;

import cz.cvut.fel.annotator.dto.mediaAsset.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.external.PlaylistResponseDto;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.exception.MediaCmsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaCmsClient {

    private final WebClient mediaCmsWebClient;
    private final MediaCmsUrlResolver urlResolver;

    public PlaylistResponseDto getPlaylist(String playlistId) {
        return get(
                urlResolver.buildApiUrl(
                        Constants.MediaCms.CATEGORY_PLAYLISTS,
                        playlistId
                ),
                PlaylistResponseDto.class
        );
    }

    public MediaCmsMediaDto getMediaById(String mediaId) {
        return get(
                urlResolver.buildApiUrl(
                        Constants.MediaCms.CATEGORY_MEDIA,
                        mediaId
                ),
                MediaCmsMediaDto.class
        );
    }

    public MediaCmsMediaDto getMediaByUrl(String mediaApiUrl) {
        return get(mediaApiUrl, MediaCmsMediaDto.class);
    }

    private <T> T get(String uri, Class<T> responseType) {
        try {
            return mediaCmsWebClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

        } catch (WebClientResponseException e) {
            throw new MediaCmsException(
                    HttpStatus.resolve(e.getStatusCode().value()),
                    uri,
                    e.getResponseBodyAsString()
            );
        }
    }
}