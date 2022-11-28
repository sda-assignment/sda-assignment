package payments.entities;

import datastore.Entity;
import payments.entities.enums.ProviderSocketName;

public class Provider implements Entity {
    public final String serviceName;
    public final String name;
    public final ProviderSocketName socketName;
    public final boolean cashOnDelivery;

    public Provider(String serviceName, String providerName, ProviderSocketName socketName, boolean cashOnDelivery) {
        this.serviceName = serviceName;
        this.name = providerName;
        this.socketName = socketName;
        this.cashOnDelivery = cashOnDelivery;
    }

    public String storify() {
        return serviceName + ":" + name + ":" + socketName + ":" + cashOnDelivery;
    }
}
