package handlers.concrete;

import java.util.HashMap;

import handlers.AbstractHandler;
import handlers.HandlerName;
import handlers.HandlerResponse;

public class ErroneousHandler extends AbstractHandler {

    public HandlerName getHandlerName() {
        return HandlerName.ERRONEOUS;
    }

    public String[] getRequestKeys() {
        return new String[] {};
    }

    public String getConstraints() {
        return "";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        return new HandlerResponse("There is no handler to process this request");
    }
}
