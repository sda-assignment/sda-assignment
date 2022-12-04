package handlers;

public class HandlerResponse {
    public final boolean success;
    public final String errorMessage;
    public final Double amount;

    public HandlerResponse(Double amount) {
        this.success = true;
        this.amount = amount;
        this.errorMessage = "";
    }

    public HandlerResponse(String errorMessage) {
        this.success = false;
        this.amount = null;
        this.errorMessage = errorMessage;
    }
}
