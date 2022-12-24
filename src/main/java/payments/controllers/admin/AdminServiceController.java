package payments.controllers.admin;

import datastore.Model;
import payments.common.Response;
import payments.entities.Service;

public class AdminServiceController {
    private Model<Service> serviceModel;

    public AdminServiceController(Model<Service> serviceModel) {
        this.serviceModel = serviceModel;
    }

    public Response addService(String serviceName) {
        if (serviceModel.recordExists(s -> s.name.equals(serviceName)))
            return new Response(false, "Service already exists");
        serviceModel.insert(new Service(serviceName));
        return new Response(true, "Service added successfully");
    }
}
