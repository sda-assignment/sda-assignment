package payments.entities;

import datastore.Entity;
import payments.Util;

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
        return Util.separateWithColons(new Object[] {transactionId, serviceName, providerName});
    }
}
