package payments.controllers.request;

import handlers.HandlerName;

public class AddProviderBody {
    public final String name;
    public final boolean cashOnDelivery;
    public final HandlerName handlerName;

    public AddProviderBody(String name, boolean cashOnDelivery, HandlerName handlerName) {
        this.name = name;
        this.cashOnDelivery = cashOnDelivery;
        this.handlerName = handlerName;
    }
}
