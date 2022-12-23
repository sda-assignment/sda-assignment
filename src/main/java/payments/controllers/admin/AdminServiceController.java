package payments.controllers.admin;

import datastore.Relation;
import payments.common.Response;
import payments.entities.Service;

public class AdminServiceController {
    private Relation<Service> serviceRelation;

    public AdminServiceController(Relation<Service> serviceRelation) {
        this.serviceRelation = serviceRelation;
    }

    public Response addService(String serviceName) {
        if (serviceRelation.recordExists(s -> s.name.equals(serviceName)))
            return new Response(false, "Service already exists");
        serviceRelation.insert(new Service(serviceName));
        return new Response(true, "Service added successfully");
    }
}
