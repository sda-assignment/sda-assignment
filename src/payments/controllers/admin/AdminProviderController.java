package payments.controllers.admin;

import java.util.ArrayList;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import handlers.HandlerName;
import payments.common.Response;
import payments.entities.FormElement;
import payments.entities.Provider;

public class AdminProviderController {
    private Relation<Provider> providerRelation;
    private Relation<FormElement> formElementRelation;

    public Response addProvider(String serviceName, String name, boolean cashOnDelivery, HandlerName handlerName)
            throws EntitySaveException {
        if (providerRelation.recordExists(p -> p.serviceName.equals(serviceName) && p.name.equals(name)))
            return new Response(false, "The provider already exists");
        providerRelation.insert(new Provider(serviceName, name, cashOnDelivery, handlerName));
        return new Response(true, "Provider created successfully");
    }

    public ArrayList<Provider> searchForProviders(String serviceNameAndProviderName) {
        return providerRelation.select(
                p -> p.serviceName.contains(serviceNameAndProviderName) || p.name.contains(serviceNameAndProviderName));
    }

    public ArrayList<Provider> getProvidersForService(String serviceName) {
        return providerRelation.select(p -> p.serviceName.equals(serviceName));
    }

    public ArrayList<FormElement> getProviderFormElements(String serviceName, String providerName) {
        return formElementRelation
                .select(fe -> fe.serviceName.equals(serviceName) && fe.providerName.equals(providerName));
    }
}
