package payments.controllers;

import java.util.ArrayList;
import datastore.Relation;
import payments.entities.FormElement;
import payments.entities.Provider;

public class ProviderController {
    private Relation<Provider> providerRelation;
    private Relation<FormElement> formElementRelation;

    public ProviderController(Relation<Provider> providerRelation, Relation<FormElement> formElementRelation) {
        this.providerRelation = providerRelation;
        this.formElementRelation = formElementRelation;
    }

    public ArrayList<Provider> searchForProviders(String serviceNameAndProviderName) {
        return providerRelation.select(
                p -> p.serviceName.contains(serviceNameAndProviderName) || p.name.contains(serviceNameAndProviderName));
    }

    public ArrayList<Provider> getProvidersForService(String serviceName) {
        return providerRelation.select(p -> p.serviceName.equals(serviceName));
    }

    public ArrayList<Provider> getAllProviders() {
        return providerRelation.select(p -> true);
    }

    public ArrayList<FormElement> getProviderFormElements(String serviceName, String providerName) {
        return formElementRelation
                .select(fe -> fe.serviceName.equals(serviceName) && fe.providerName.equals(providerName));
    }
}