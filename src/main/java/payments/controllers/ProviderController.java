package payments.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import datastore.Model;
import payments.entities.Provider;

@RestController
public class ProviderController {
    private Model<Provider> providerModel;

    public ProviderController(Model<Provider> providerModel) {
        this.providerModel = providerModel;
    }

    @GetMapping("/providers")
    @ResponseBody
    public ArrayList<Provider> listProviders(@RequestParam(required = false) String name) {
        return providerModel
                .select(p -> (name == null || p.serviceName.toLowerCase().contains(name.toLowerCase())
                        || p.name.toLowerCase().contains(name.toLowerCase())));
    }
}
