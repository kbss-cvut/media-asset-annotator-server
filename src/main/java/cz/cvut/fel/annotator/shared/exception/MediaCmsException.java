package cz.cvut.fel.annotator.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MediaCmsException extends ApiException {

    private final String rawBody;
    private final String uri;

    public MediaCmsException(HttpStatus status, String uri, String rawBody) {
        super(status, "MEDIA_CMS_ERROR",
                String.format("MediaCMS request failed [%s]: %s", status, uri));
        this.rawBody = rawBody;
        this.uri = uri;
    }

    public MediaCmsException(String uri, String message) {
        super(HttpStatus.BAD_GATEWAY, "MEDIA_CMS_ERROR",
                String.format("MediaCMS request failed: %s (%s)", message, uri));
        this.rawBody = null;
        this.uri = uri;
    }
}