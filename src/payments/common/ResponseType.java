package payments.common;

public class ResponseType {
    public final boolean success;
    public final String status;

    public ResponseType(boolean success, String status) {
        this.success = success;
        this.status = status;
    }
}
