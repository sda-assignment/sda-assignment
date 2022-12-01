package payments.handlers;

import java.util.HashMap;

import common.HandlerName;
import common.Response;

public abstract class Handler {
    protected abstract boolean isRequestValid();

    protected abstract void handleRequest(HashMap<String, String> request);

    protected abstract double getAmount(HashMap<String, String> request);

    public Response<Double> handleRequestAndReturnAmount(HashMap<String, String> request) {
        if (!isRequestValid())
            return new Response<Double>(false, null);
        handleRequest(request);
        return new Response<Double>(true, getAmount(request));
    }

    public abstract HandlerName getHandlerName();
}
