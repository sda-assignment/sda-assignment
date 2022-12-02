package handlers;

import java.util.HashMap;

public interface IHandler {
    HandlerName getHandlerName();

    String[] getRequestKeys();

    String getConstraints();

    HandlerResponse validateAndHandleRequest(HashMap<String, String> request);
}
