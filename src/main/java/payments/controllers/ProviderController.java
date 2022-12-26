package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import datastore.Model;
import payments.controllers.auth.Authenticator;
import payments.entities.FormElement;
import payments.entities.Provider;

@RestController
public class ProviderController {
    private Model<Provider> providerModel;
    private Model<FormElement> formElementModel;
    private Authenticator authenticator;

    public ProviderController(Model<Provider> providerModel, Model<FormElement> formElementModel,
            Authenticator authenticator) {
        this.providerModel = providerModel;
        this.formElementModel = formElementModel;
        this.authenticator = authenticator;
    }

    @GetMapping("/providers")
    public ArrayList<Provider> searchForProviders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam(required = false) String simpleName, @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) String name) {
        authenticator.getContextOrFail(authHeader);
        return providerModel
                .select(p -> (simpleName == null || p.serviceName.toLowerCase().contains(simpleName.toLowerCase())) &&
                        (serviceName == null || p.serviceName.equals(serviceName)) &&
                        (name == null || p.name.equals(name)));
    }

    @GetMapping("/providers/{name}/{serviceName}")
    public Provider getProvider(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("name") String name, @PathVariable("serviceName") String serviceName) {
        authenticator.getContextOrFail(authHeader);
        ArrayList<Provider> providers = providerModel
                .select(p -> p.serviceName.equals(serviceName) && p.name.equals(name));
        if (providers.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No provider with this name providing this service was found");
        }
        return providers.get(0);
    }

    @GetMapping("/providers/{name}/{serviceName}/form-elements")
    public ArrayList<FormElement> getProviderFormElements(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("name") String name, @PathVariable("serviceName") String serviceName) {
        authenticator.getContextOrFail(authHeader);
        getProvider(authHeader, name, serviceName); // Fail if the provider is not found
        return formElementModel
                .select(fe -> fe.serviceName.equals(serviceName) && fe.providerName.equals(name));
    }
}
