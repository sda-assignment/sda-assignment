package datastore.exceptions;

public class RecordListLoadException extends RecordListException {
    public RecordListLoadException(String message) {
        super("Failed to load record list: " + message);
    }
}
