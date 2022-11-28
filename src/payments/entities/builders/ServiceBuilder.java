package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.Service;

public class ServiceBuilder implements EntityBuilder<Service> {
    public Service fromString(String serviceName) {
        return new Service(serviceName);
    }
}
