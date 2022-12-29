package payments.controllers.response;

import handlers.Handler;

public class HandlerResponse {
    public final String[] requiredRequestAttributes;
    public final String constraints;

    public HandlerResponse(Handler handler) {
        this.requiredRequestAttributes = handler.getRequestKeys();
        this.constraints = handler.getConstraints();
    }
}
