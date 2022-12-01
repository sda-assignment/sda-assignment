package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.Response;
import payments.entities.Provider;

public class ProviderController {
    private Relation<Provider> providerRelation;

    public Response addProvider(String serviceName, String name, boolean cashOnDelivery) throws EntitySaveException {
        if (providerRelation.recordExists(p -> p.serviceName.equals(serviceName) && p.name.equals(name)))
            return new Response(false, "The provider already exists");
        providerRelation.insert(new Provider(serviceName, name, cashOnDelivery));
        return new Response(true, "Provider created successfully");
    }

    public ArrayList<Provider> searchForProviders(String serviceNameAndProviderName) {
        return providerRelation.select(
                p -> p.serviceName.contains(serviceNameAndProviderName) || p.name.contains(serviceNameAndProviderName));
    }

    public ArrayList<Provider> getProvidersForService(String serviceName) {
        return providerRelation.select(p -> p.serviceName.equals(serviceName));
    }
}
