package payments.controllers.admin;

import datastore.Model;
import handlers.HandlerName;
import payments.common.Response;
import payments.entities.Provider;

public class AdminProviderController {
    private Model<Provider> providerModel;

    public Response addProvider(String serviceName, String name, boolean cashOnDelivery, HandlerName handlerName)
            {
        if (providerModel.recordExists(p -> p.serviceName.equals(serviceName) && p.name.equals(name)))
            return new Response(false, "The provider already exists");
        providerModel.insert(new Provider(serviceName, name, cashOnDelivery, handlerName));
        return new Response(true, "Provider created successfully");
    }
}
