package datastore.exceptions;

public class EntityInitializationException extends EntityException {
    public EntityInitializationException(String message) {
        super("Failed to initialize record list: " + message);
    }
}
