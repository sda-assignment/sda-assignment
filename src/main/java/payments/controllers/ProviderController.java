package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import datastore.Model;
import payments.controllers.auth.Authenticator;
import payments.entities.Provider;

@RestController
public class ProviderController {
    private Model<Provider> providerModel;
    private Authenticator authenticator;

    public ProviderController(Model<Provider> providerModel, Authenticator authenticator) {
        this.providerModel = providerModel;
        this.authenticator = authenticator;
    }

    @GetMapping("/providers")
    @ResponseBody
    public ArrayList<Provider> listProviders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam(required = false) String name) {
        authenticator.getContextOrFail(authHeader);
        return providerModel
                .select(p -> (name == null || p.serviceName.toLowerCase().contains(name.toLowerCase())
                        || p.name.toLowerCase().contains(name.toLowerCase())));
    }
}
