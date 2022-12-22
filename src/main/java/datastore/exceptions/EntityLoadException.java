package datastore.exceptions;

public class EntityLoadException extends EntityException {
    public EntityLoadException(String message) {
        super("Failed to load record list: " + message);
    }
}
