package payments.controllers.admin;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import handlers.HandlerName;
import payments.common.Response;
import payments.entities.Provider;

public class AdminProviderController {
    private Relation<Provider> providerRelation;

    public Response addProvider(String serviceName, String name, boolean cashOnDelivery, HandlerName handlerName)
            throws EntitySaveException {
        if (providerRelation.recordExists(p -> p.serviceName.equals(serviceName) && p.name.equals(name)))
            return new Response(false, "The provider already exists");
        providerRelation.insert(new Provider(serviceName, name, cashOnDelivery, handlerName));
        return new Response(true, "Provider created successfully");
    }
}
