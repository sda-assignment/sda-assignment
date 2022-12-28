package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import datastore.Model;
import payments.entities.Discount;
import payments.entities.FormElement;
import payments.entities.FormElementChoice;
import payments.entities.Provider;
import payments.entities.Service;
import payments.enums.FormElementType;

@RestController
public class ServiceController {
    private Model<Service> serviceModel;
    private Model<Provider> providerModel;
    private Model<Discount> discountModel;
    private Model<FormElement> formElementModel;
    private Model<FormElementChoice> formElementChoiceModel;

    public ServiceController(Model<Service> serviceModel, Model<Provider> providerModel, Model<Discount> discountModel,
            Model<FormElement> formElementModel, Model<FormElementChoice> formElementChoiceModel) {
        this.serviceModel = serviceModel;
        this.providerModel = providerModel;
        this.discountModel = discountModel;
        this.formElementModel = formElementModel;
        this.formElementChoiceModel = formElementChoiceModel;
    }

    @GetMapping("/services")
    @ResponseBody
    public ArrayList<Service> listServices(@RequestParam(required = false) String serviceName) {
        return serviceModel
                .select(s -> serviceName == null || s.name.toLowerCase().contains(serviceName.toLowerCase()));
    }

    @GetMapping("/services/{serviceName}")
    @ResponseBody
    public Service getService(@PathVariable("serviceName") String serviceName) {
        Service service = serviceModel.selectOne(s -> s.name.equals(serviceName));
        if (service == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a service with this name");
        return service;
    }

    @GetMapping("/services/{serviceName}/providers")
    @ResponseBody
    public ArrayList<Provider> listProvidersForService(@PathVariable("serviceName") String serviceName) {
        getService(serviceName); // Fail if doesn't exist
        return providerModel.select(p -> p.serviceName.equals(serviceName));
    }

    @GetMapping("/services/{serviceName}/discounts")
    @ResponseBody
    public ArrayList<Discount> listDiscountsForService(@PathVariable("serviceName") String serviceName) {
        getService(serviceName); // Fail if doesn't exist
        return discountModel.select(d -> d.serviceName.equals(serviceName) && d.isActive);
    }

    @GetMapping("/services/{serviceName}/providers/{providerName}")
    @ResponseBody
    public Provider getServiceProvider(
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName) {
        getService(serviceName); // Fail if doesn't exist
        Provider provider = providerModel
                .selectOne(p -> p.serviceName.equals(serviceName) && p.name.equals(providerName));
        if (provider == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Could not find a provider with such a name providing such a service");
        return provider;
    }

    @GetMapping("/services/{serviceName}/providers/{providerName}/form-elements")
    @ResponseBody
    public ArrayList<FormElement> listServiceProviderFormElements(
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName) {
        getServiceProvider(serviceName, providerName); // Fail if doesn't exist
        return formElementModel
                .select(fe -> fe.serviceName.equals(serviceName) && fe.providerName.equals(providerName));
    }

    @GetMapping("/services/{serviceName}/providers/{providerName}/form-elements/{formElementName}")
    @ResponseBody
    public FormElement getServiceProviderFormElement(
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName,
            @PathVariable("formElementName") String formElementName) {
        getServiceProvider(serviceName, providerName); // Fail if doesn't exist
        FormElement formElement = formElementModel.selectOne(fe -> fe.serviceName.equals(serviceName)
                && fe.providerName.equals(providerName) && fe.name.equals(formElementName));
        if (formElement == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Could not find such a form element in such a provider providing such a service");

        return formElement;
    }

    @GetMapping("/services/{serviceName}/providers/{providerName}/form-elements/{formElementName}/choices")
    @ResponseBody
    public ArrayList<FormElementChoice> listServiceProviderFormElementChoices(
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName,
            @PathVariable("formElementName") String formElementName) {
        FormElement formElement = getServiceProviderFormElement(serviceName, providerName, formElementName);

        if (formElement.type != FormElementType.DROP_DOWN_FIELD)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only drop down form elements provide choices");

        return formElementChoiceModel.select(fec -> fec.formElementName.equals(formElement.name));
    }

}
