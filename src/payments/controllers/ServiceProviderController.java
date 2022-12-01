package payments.controllers;

import java.util.ArrayList;

import payments.common.Response;

import payments.entities.FormElement;
import payments.entities.Provider;
import payments.entities.Service;
import payments.entities.enums.ProviderSocketName;
import payments.entities.enums.FormElementType;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;

public class ServiceProviderController {
    private Relation<FormElement> formElement;
    private Relation<Service> service;
    private Relation<Provider> provider;

    public ServiceProviderController(Relation<FormElement> f, Relation<Provider> p, Relation<Service> s) {
        this.formElement = f;
        this.provider = p;
        this.service = s;
    }

    public Response createServiceProvider(String serviceName, String providerName, ProviderSocketName socketName,
            boolean cashOnDelivery) throws EntitySaveException {

        ArrayList<Provider> pArray = provider.select(p -> true);
        ArrayList<Service> sArray = service.select(s -> s.name.equals(serviceName));
        if (sArray.size() == 0) {
            return new Response(false, "Failed to add provider : The Service " + serviceName + " is not supported \n");
        }
        for (Provider record : pArray) {
            if (providerName.equals(record.name)) {
                return new Response(false, "Failed to add provider : The Service Provider name already exists \n");
            }
        }
        ProviderSocketName[] eArray = ProviderSocketName.values();
        for (ProviderSocketName name : eArray) {
            if (name.equals(socketName)) {
                Provider newProvider = new Provider(serviceName, providerName, socketName, cashOnDelivery);
                provider.insert(newProvider);
                return new Response(true, "Provider " + providerName + " has been added successfully \n");
            }
        }
        return new Response(false, "Failed to add provider : The socket name does not exist \n");

    }

    public Response createFormField(String name, String serviceName, String providerName, FormElementType type,
            String info, boolean hasDeductionAmount) throws EntitySaveException {

        ArrayList<Provider> pArray = provider.select(p -> true);
        ArrayList<Service> sArray = service.select(s -> s.name.equals(serviceName));
        if (sArray.size() == 0) {
            return new Response(false, "Failed to add element : The Service " + serviceName + " is not supported \n");
        }
        for (Provider record : pArray) {
            if (providerName.equals(record.name)) {

                FormElementType[] fArray = FormElementType.values();
                for (FormElementType formType : fArray) {
                    if (formType.equals(type)) {
                        FormElement element = new FormElement(name, serviceName, providerName, type, info,
                                hasDeductionAmount);
                        formElement.insert(element);
                        return new Response(true, "Form Element Added Successfully");
                    }
                }

                return new Response(false, "Failed to add form element : Form element type is not supported \n");
            }
        }
        return new Response(false, "Failed to add form element : The Service Provider name does not exist \n");
    }

}
