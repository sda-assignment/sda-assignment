package common;

public class Response<T> {
    public final boolean success;
    public final T value;

    public Response(boolean success, T status) {
        this.success = success;
        this.value = status;
    }
}
