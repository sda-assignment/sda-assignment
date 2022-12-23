package payments.controllers.admin;

import datastore.Relation;
import payments.common.Response;
import payments.common.enums.FormElementType;
import payments.entities.FormElement;
import payments.entities.FormElementChoice;

public class AdminFormElementController {
    private Relation<FormElement> formElementRelation;
    private Relation<FormElementChoice> formElementChoiceRelation;

    public Response addFormElement(String name, String serviceName, String providerName, FormElementType type,
            String info) {
        if (formElementRelation.recordExists(
                fe -> fe.name.equals(name) && fe.serviceName.equals(serviceName)
                        && fe.providerName.equals(providerName)))
            return new Response(false, "This form element already exists");
        formElementRelation
                .insert(new FormElement(name, serviceName, providerName, type, info));
        return new Response(true, "Form element added successfully");
    }

    public Response addFormElementChoice(String info, String serviceName, String providerName)
            {
        formElementChoiceRelation.insert(new FormElementChoice(info, serviceName, providerName));
        return new Response(true, "Added the form element choice");
    }
}
