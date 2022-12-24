package payments.controllers.request;

import java.util.HashMap;

public class CreditCardPaymentBody {
    public final String serviceName;
    public final String providerName;
    public final HashMap<String, String> handlerRequest;
    public final String cardNumber;

    public CreditCardPaymentBody(String serviceName, String providerName, HashMap<String, String> handlerRequest,
            String cardNumber) {
        this.serviceName = serviceName;
        this.providerName = providerName;
        this.handlerRequest = new HashMap<>(handlerRequest);
        this.cardNumber = cardNumber;
    }
}
