package cz.cvut.fel.annotator.shared.exception;

import org.springframework.http.HttpStatus;

public class RecordManagerException extends ApiException {


    public RecordManagerException(HttpStatus status, String uri, String rawBody) {
        super(status, "MEDIA_CMS_ERROR",
                String.format("MediaCMS request failed [%s]: %s", status, uri));
    }


}
