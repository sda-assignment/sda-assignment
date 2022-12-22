package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import payments.entities.Service;

public class ServiceController {
    private Relation<Service> serviceRelation;

    public ServiceController(Relation<Service> serviceRelation) {
        this.serviceRelation = serviceRelation;
    }

    public ArrayList<Service> getAllServices() {
        return serviceRelation.select(s -> true);
    }
}
