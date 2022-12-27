package payments.entities;

import handlers.HandlerName;

public class Provider {
    public final String serviceName;
    public final String name;
    public final boolean cashOnDelivery;
    public final HandlerName handlerName;

    public Provider(String serviceName, String providerName, boolean cashOnDelivery, HandlerName handlerName) {
        this.serviceName = serviceName;
        this.name = providerName;
        this.cashOnDelivery = cashOnDelivery;
        this.handlerName = handlerName;
    }
}
