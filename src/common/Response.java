package payments.common;

public class Response {
    public final boolean success;
    public final String status;

    public Response(boolean success, String status) {
        this.success = success;
        this.status = status;
    }
}
