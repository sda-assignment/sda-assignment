package payments.controllers.request;

import java.util.HashMap;

public class PaymentBody {
    public final HashMap<String, String> handlerRequest;

    public PaymentBody(String serviceName, String providerName, HashMap<String, String> handlerRequest) {
        this.handlerRequest = new HashMap<>(handlerRequest);
    }
}
