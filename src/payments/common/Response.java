package payments.common;

public class Response {
    public final boolean success;
    public final String value;

    public Response(boolean success, String value) {
        this.success = success;
        this.value = value;
    }
}
