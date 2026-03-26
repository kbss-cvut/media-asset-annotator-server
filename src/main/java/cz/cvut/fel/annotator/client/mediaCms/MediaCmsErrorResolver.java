package cz.cvut.fel.annotator.client.mediaCms;

import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.exception.EntityNotFoundException;
import cz.cvut.fel.annotator.shared.exception.MediaCmsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaCmsErrorResolver {

    private final ObjectMapper objectMapper;

    public RuntimeException resolve(MediaCmsException ex, String context) {
        String reason = extractReason(ex.getRawBody());
        HttpStatus status = ex.getStatus();
        String uri = ex.getUri();

        if (status == HttpStatus.NOT_FOUND) {
            log.warn(Constants.MediaCms.LOG_404, context, reason);
            return new EntityNotFoundException(
                    "MediaCMS resource",
                    context + " (" + uri + ")"
            );
        }

        if (status.is4xxClientError()) {
            log.error(Constants.MediaCms.LOG_ERR, status.value(), context, reason);

            return new MediaCmsException(
                    status,
                    uri,
                    ex.getRawBody()
            );
        }

        if (status.is5xxServerError()) {
            log.error(Constants.MediaCms.LOG_5XX, context, reason);

            return new MediaCmsException(
                    status,
                    uri,
                    ex.getRawBody()
            );
        }

        log.error("Unexpected MediaCMS error status={} context={} reason={}",
                status, context, reason);

        return new MediaCmsException(
                HttpStatus.BAD_GATEWAY,
                uri,
                ex.getRawBody()
        );
    }

    private String extractReason(String body) {
        if (body == null || body.isBlank()) {
            return Constants.MediaCms.UNKNOWN_ERROR;
        }

        try {
            MediaCmsErrorDto dto =
                    objectMapper.readValue(body, MediaCmsErrorDto.class);

            return dto.detail() != null
                    ? dto.detail()
                    : Constants.MediaCms.UNKNOWN_ERROR;

        } catch (Exception ignored) {
            return Constants.MediaCms.UNKNOWN_ERROR;
        }
    }
}