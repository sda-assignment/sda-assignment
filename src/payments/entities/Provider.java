package payments.entities;

import datastore.Entity;

public class Provider implements Entity {
    public final String serviceName;
    public final String name;
    public final boolean cashOnDelivery;

    public Provider(String serviceName, String providerName, boolean cashOnDelivery) {
        this.serviceName = serviceName;
        this.name = providerName;
        this.cashOnDelivery = cashOnDelivery;
    }

    public String storify() {
        return serviceName + ":" + name + ":" + cashOnDelivery;
    }
}
