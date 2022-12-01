package payments.entities;

import datastore.Entity;

public class ServiceProviderTransaction implements Entity {
    public final int transactionId;
    public final String serviceName;
    public final String providerName;

    public ServiceProviderTransaction(int transactionId, String serviceName, String providerName) {
        this.transactionId = transactionId;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }

    public String storify() {
        return transactionId + ":" + serviceName + ":" + providerName;
    }
}
