package payments.handlers;

public abstract class Handler {
    protected abstract boolean isRequestValid();
    protected abstract void handleRequest();
    protected abstract double getAmount();
    // public double handleRequestAndReturnAmount() {

    // }
    // HandlerName getHandlerName();
}
