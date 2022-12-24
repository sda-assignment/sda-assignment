package payments.controllers;

import java.util.ArrayList;

import datastore.Model;
import payments.entities.Service;

public class ServiceController {
    private Model<Service> serviceModel;

    public ServiceController(Model<Service> serviceModel) {
        this.serviceModel = serviceModel;
    }

    public ArrayList<Service> getAllServices() {
        return serviceModel.select(s -> true);
    }
}
