package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import datastore.Model;
import payments.controllers.auth.Authenticator;
import payments.entities.Discount;
import payments.entities.FormElement;
import payments.entities.Provider;
import payments.entities.Service;

@RestController
public class ServiceController {
    private Model<Service> serviceModel;
    private Model<Provider> providerModel;
    private Model<Discount> discountModel;
    private Model<FormElement> formElementModel;
    private Authenticator authenticator;

    public ServiceController(Model<Service> serviceModel, Model<Provider> providerModel, Model<Discount> discountModel,
            Model<FormElement> formElementModel, Authenticator authenticator) {
        this.serviceModel = serviceModel;
        this.providerModel = providerModel;
        this.discountModel = discountModel;
        this.authenticator = authenticator;
        this.formElementModel = formElementModel;
    }

    @GetMapping("/services")
    @ResponseBody
    public ArrayList<Service> getAllServices(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam(required = false) String serviceName) {
        authenticator.getContextOrFail(authHeader);
        return serviceModel
                .select(s -> serviceName == null || s.name.toLowerCase().contains(serviceName.toLowerCase()));
    }

    @GetMapping("/services/{serviceName}")
    @ResponseBody
    public Service getService(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("serviceName") String serviceName) {
        authenticator.getContextOrFail(authHeader);
        Service service = serviceModel.selectOne(s -> s.name.equals(serviceName));
        if (service == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a service with this name");
        return service;
    }

    @GetMapping("/services/{serviceName}/providers")
    @ResponseBody
    public ArrayList<Provider> getProvidersForService(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("serviceName") String serviceName) {
        authenticator.getContextOrFail(authHeader);
        getService(authHeader, serviceName); // Fail if doesn't exist
        return providerModel.select(p -> p.serviceName.equals(serviceName));
    }

    @GetMapping("/services/{serviceName}/discounts")
    @ResponseBody
    public ArrayList<Discount> getDiscountsForService(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("serviceName") String serviceName) {
        authenticator.getContextOrFail(authHeader);
        getService(authHeader, serviceName); // Fail if doesn't exist
        return discountModel.select(d -> d.serviceName.equals(serviceName));
    }

    @GetMapping("/services/{serviceName}/providers/{providerName}")
    @ResponseBody
    public Provider getServiceProvider(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName) {
        authenticator.getContextOrFail(authHeader);
        getService(authHeader, serviceName); // Fail if doesn't exist
        Provider provider = providerModel
                .selectOne(p -> p.serviceName.equals(serviceName) && p.name.equals(providerName));
        if (provider == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Could not find a provider with such a name providing such a service");
        return provider;
    }

    @GetMapping("/services/{serviceName}/providers/{providerName}/form-elements")
    @ResponseBody
    public ArrayList<FormElement> getServiceProviderFormElements(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName) {
        authenticator.getContextOrFail(authHeader);
        getService(authHeader, serviceName); // Fail if doesn't exist
        getServiceProvider(authHeader, serviceName, providerName); // Fail if doesn't exist
        return formElementModel
                .select(fe -> fe.serviceName.equals(serviceName) && fe.providerName.equals(providerName));
    }
}
