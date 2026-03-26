package cz.cvut.fel.annotator.shared.exception;

public class EntityNotFoundException extends ResourceNotFoundException {

    public EntityNotFoundException(String entity, String id) {
        super(String.format("%s not found: %s", entity, id));
    }
}