package cz.cvut.fel.annotator.client.recordManager;

import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDtoLD;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.exception.RecordManagerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecordManagerClient {

    private final WebClient recordManagerWebClient;

    public void putMediaAsset(String referenceId, MediaAssetDtoLD dto) {
        String path = "/%s/%s".formatted(
                Constants.RecordManager.ASSET_UPDATE_LISTENER_PATH,
                referenceId
        );
        put(path, dto);
    }

    private <T> void put(String path, T body) {
        try {
            log.debug("{} PUT path={}", Constants.Log.RECORD_MANAGER_CLIENT, path);

            recordManagerWebClient.put()
                    .uri(path)
                    .bodyValue(body)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            log.debug("{} PUT success path={}", Constants.Log.RECORD_MANAGER_CLIENT, path);

        } catch (WebClientResponseException e) {
            log.error("{} PUT failed path={} status={} body={}",
                    Constants.Log.RECORD_MANAGER_CLIENT,
                    path,
                    e.getStatusCode().value(),
                    e.getResponseBodyAsString()
            );

            throw new RecordManagerException(
                    HttpStatus.resolve(e.getStatusCode().value()),
                    path,
                    e.getResponseBodyAsString()
            );
        }
    }
}