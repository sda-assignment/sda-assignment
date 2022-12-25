package payments.controllers;

import java.util.ArrayList;
import datastore.Model;
import payments.entities.FormElement;
import payments.entities.Provider;

public class ProviderController {
    private Model<Provider> providerModel;
    private Model<FormElement> formElementModel;

    public ProviderController(Model<Provider> providerModel, Model<FormElement> formElementModel) {
        this.providerModel = providerModel;
        this.formElementModel = formElementModel;
    }

    public ArrayList<Provider> searchForProviders(String serviceNameAndProviderName) {
        return providerModel.select(
                p -> p.serviceName.toLowerCase().contains(serviceNameAndProviderName.toLowerCase())
                        || p.name.toLowerCase().contains(serviceNameAndProviderName.toLowerCase()));
    }

    public ArrayList<Provider> getProvidersForService(String serviceName) {
        return providerModel.select(p -> p.serviceName.equals(serviceName));
    }

    public ArrayList<Provider> getAllProviders() {
        return providerModel.select(p -> true);
    }

    public ArrayList<FormElement> getProviderFormElements(String serviceName, String providerName) {
        return formElementModel
                .select(fe -> fe.serviceName.equals(serviceName) && fe.providerName.equals(providerName));
    }
}
