package payments.entities;

import common.HandlerName;
import common.Util;
import datastore.Entity;

public class Provider implements Entity {
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

    public String storify() {
        return Util.separateWithColons(new Object[] { serviceName, name, cashOnDelivery, handlerName });
    }
}
