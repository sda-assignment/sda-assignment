package payments.controllers;

import java.util.ArrayList;

import common.HandlerName;
import common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.entities.Provider;

public class ProviderController {
    private Relation<Provider> providerRelation;

    public Response<String> addProvider(String serviceName, String name, boolean cashOnDelivery, HandlerName handlerName)
            throws EntitySaveException {
        if (providerRelation.recordExists(p -> p.serviceName.equals(serviceName) && p.name.equals(name)))
            return new Response<String>(false, "The provider already exists");
        providerRelation.insert(new Provider(serviceName, name, cashOnDelivery, handlerName));
        return new Response<String>(true, "Provider created successfully");
    }

    public ArrayList<Provider> searchForProviders(String serviceNameAndProviderName) {
        return providerRelation.select(
                p -> p.serviceName.contains(serviceNameAndProviderName) || p.name.contains(serviceNameAndProviderName));
    }

    public ArrayList<Provider> getProvidersForService(String serviceName) {
        return providerRelation.select(p -> p.serviceName.equals(serviceName));
    }
}
