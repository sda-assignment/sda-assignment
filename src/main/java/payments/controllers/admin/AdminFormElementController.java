package payments.controllers.admin;

import datastore.Model;
import payments.common.Response;
import payments.common.enums.FormElementType;
import payments.entities.FormElement;
import payments.entities.FormElementChoice;

public class AdminFormElementController {
    private Model<FormElement> formElementModel;
    private Model<FormElementChoice> formElementChoiceModel;

    public Response addFormElement(String name, String serviceName, String providerName, FormElementType type,
            String info) {
        if (formElementModel.recordExists(
                fe -> fe.name.equals(name) && fe.serviceName.equals(serviceName)
                        && fe.providerName.equals(providerName)))
            return new Response(false, "This form element already exists");
        formElementModel
                .insert(new FormElement(name, serviceName, providerName, type, info));
        return new Response(true, "Form element added successfully");
    }

    public Response addFormElementChoice(String info, String formElementName, String serviceName, String providerName) {
        formElementChoiceModel.insert(new FormElementChoice(info, formElementName, serviceName, providerName));
        return new Response(true, "Added the form element choice");
    }
}
