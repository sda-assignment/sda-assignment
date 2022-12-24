package payments.controllers.request;

import java.util.HashMap;

public class PaymentBody {
    public final String serviceName;
    public final String providerName;
    public final HashMap<String, String> handlerRequest;

    public PaymentBody(String serviceName, String providerName, HashMap<String, String> handlerRequest) {
        this.serviceName = serviceName;
        this.providerName = providerName;
        this.handlerRequest = new HashMap<>(handlerRequest);
    }
}
