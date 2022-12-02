package handlers;

import java.util.HashMap;

import common.HandlerName;
import common.HandlerResponse;

public abstract class Handler {
    public abstract HandlerName getHandlerName();

    public abstract String[] getRequestKeys();

    public abstract String getConstraints();

    protected abstract HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request);

    protected boolean requestContainsAllKeys(HashMap<String, String> request) {
        String[] keys = getRequestKeys();
        for (String key : keys) {
            if (!request.containsKey(key))
                return false;
        }
        return true;
    }

    public HandlerResponse handleRequestAndReturnAmount(HashMap<String, String> request) {
        if (!requestContainsAllKeys(request))
            return new HandlerResponse("The request does not contain all the required information");
        HandlerResponse res = handleRequestAndGetAmount(request);
        if (!res.success)
            return res;
        return new HandlerResponse(res.amount);
    }

}
