package datastore.exceptions;

public class EntitySaveException extends EntityException {
    public EntitySaveException(String message) {
        super("Failed to save record list: " + message);
    }
}
