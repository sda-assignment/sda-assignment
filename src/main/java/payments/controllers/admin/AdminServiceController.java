package payments.controllers.admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import datastore.Model;
import payments.controllers.ServiceController;
import payments.controllers.request.AddFormElementBody;
import payments.controllers.request.AddFormElementChoiceBody;
import payments.controllers.request.AddProviderBody;
import payments.controllers.request.AddServiceBody;
import payments.entities.FormElement;
import payments.entities.FormElementChoice;
import payments.entities.Provider;
import payments.entities.Service;

@RestController
public class AdminServiceController {
    private Model<Service> serviceModel;
    private Model<Provider> providerModel;
    private ServiceController serviceController;
    private Model<FormElement> formElementModel;
    private Model<FormElementChoice> formElementChoiceModel;

    public AdminServiceController(Model<Service> serviceModel, Model<Provider> providerModel,
            ServiceController serviceController, Model<FormElement> formElementModel,
            Model<FormElementChoice> formElementChoiceModel) {
        this.serviceModel = serviceModel;
        this.providerModel = providerModel;
        this.serviceController = serviceController;
        this.formElementModel = formElementModel;
        this.formElementChoiceModel = formElementChoiceModel;
    }

    @PostMapping("/admin/services")
    public void addService(@RequestBody AddServiceBody body) {
        if (serviceModel.recordExists(s -> s.name.equals(body.name)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service already exists");
        serviceModel.insert(new Service(body.name));
    }

    @PostMapping("/admin/services/{serviceName}/providers")
    public void addProvider(@PathVariable("serviceName") String serviceName, @RequestBody AddProviderBody body) {
        serviceController.getService(serviceName);
        if (providerModel.recordExists(p -> p.serviceName.equals(serviceName) && p.name.equals(body.name)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provider already exists");
        providerModel.insert(new Provider(serviceName, body.name, body.cashOnDelivery, body.handlerName));
    }

    @PostMapping("/admin/services/{serviceName}/providers/{providerName}/form-elements")
    public void addFormElement(@PathVariable("serviceName") String serviceName,
            @PathVariable("providerName") String providerName, @RequestBody AddFormElementBody body) {
        if (formElementModel.recordExists(
                fe -> fe.name.equals(body.name) && fe.serviceName.equals(serviceName)
                        && fe.providerName.equals(providerName)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This form element already exists");
        serviceController.getServiceProvider(serviceName, providerName);
        formElementModel
                .insert(new FormElement(body.name, serviceName, providerName, body.type, body.info));
    }

    @PostMapping("/admin/services/{serviceName}/providers/{providerName}/form-elements/{formElementName}/choices")
    public void addFormElementChoice(@PathVariable("serviceName") String serviceName,
            @PathVariable("providerName") String providerName, @PathVariable("formElementName") String formElementName,
            @RequestBody AddFormElementChoiceBody body) {
        serviceController.getServiceProviderFormElement(serviceName, providerName, formElementName);
        if (formElementChoiceModel.recordExists(fec -> fec.serviceName.equals(serviceName)
                && fec.providerName.equals(providerName) && fec.info.equals(body.info)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This form element choice already exists");
        formElementChoiceModel.insert(new FormElementChoice(body.info, formElementName, serviceName, providerName));
    }

}
