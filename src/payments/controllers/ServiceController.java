package payments.controllers;

import java.util.ArrayList;

import common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.entities.Service;

public class ServiceController {
    private Relation<Service> serviceRelation;

    public ServiceController(Relation<Service> serviceRelation) {
        this.serviceRelation = serviceRelation;
    }

    public Response<String> addService(String serviceName) throws EntitySaveException {
        if (serviceRelation.recordExists(s -> s.name.equals(serviceName)))
            return new Response<String>(false, "Service already exists");
        serviceRelation.insert(new Service(serviceName));
        return new Response<String>(true, "Service added successfully");
    }

    public ArrayList<Service> getAllServices() {
        return serviceRelation.select(s -> true);
    }
}
