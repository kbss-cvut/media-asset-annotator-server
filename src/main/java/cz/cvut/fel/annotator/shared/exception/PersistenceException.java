package cz.cvut.fel.annotator.shared.exception;

import org.springframework.http.HttpStatus;

public class PersistenceException extends ApiException {

    public PersistenceException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "PERSISTENCE_ERROR", message);
    }

    public PersistenceException(Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "PERSISTENCE_ERROR", cause.getMessage(), cause);
    }
}