package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.ServiceProviderTransaction;

public class ServiceProviderTransactionBuilder implements EntityBuilder<ServiceProviderTransaction> {
    public ServiceProviderTransaction fromString(String serviceProviderTransaction) {
        String[] splitted = serviceProviderTransaction.split(":");
        return new ServiceProviderTransaction(Integer.parseInt(splitted[0]), splitted[1], splitted[2]);
    }
}
