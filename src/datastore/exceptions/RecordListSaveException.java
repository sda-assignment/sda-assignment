package datastore.exceptions;

public class RecordListSaveException extends RecordListException {
    public RecordListSaveException(String message) {
        super("Failed to save record list: " + message);
    }
}
