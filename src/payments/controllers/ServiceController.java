package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.Response;
import payments.entities.Service;

public class ServiceController {
    private Relation<Service> serviceRelation;

    public ServiceController(Relation<Service> serviceRelation) {
        this.serviceRelation = serviceRelation;
    }

    public Response addService(String serviceName) throws EntitySaveException {
        if (serviceRelation.entityExists(s -> s.name.equals(serviceName)))
            return new Response(false, "Service already exists");
        serviceRelation.insert(new Service(serviceName));
        return new Response(true, "Service added successfully");
    }

    public ArrayList<Service> getAllServices() {
        return serviceRelation.select(s -> true);
    }
}
