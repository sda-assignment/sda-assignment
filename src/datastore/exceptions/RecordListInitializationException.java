package datastore.exceptions;

public class RecordListInitializationException extends RecordListException {
    public RecordListInitializationException(String message) {
        super("Failed to initialize record list: " + message);
    }
}
