package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.ServiceProviderTransaction;

public class ServiceProviderTransactionBuilder implements EntityBuilder<ServiceProviderTransaction> {
    public ServiceProviderTransaction fromString(String serviceProviderTransaction) {
        String[] splitted = serviceProviderTransaction.split(":");
        int i = 0;
        return new ServiceProviderTransaction(Integer.parseInt(splitted[i++]), splitted[i++], splitted[i++]);
    }
}
