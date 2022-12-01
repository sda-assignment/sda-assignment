package payments.controllers;

import common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.entities.FormElement;
import payments.entities.enums.FormElementType;

public class FormElementController {
    private Relation<FormElement> formElementRelation;

    public Response addFormElement(String name, String serviceName, String providerName, FormElementType type,
            String info) throws EntitySaveException {
        if (formElementRelation.recordExists(
                fe -> fe.name.equals(name) && fe.serviceName.equals(serviceName)
                        && fe.providerName.equals(providerName)))
            return new Response(false, "This form element already exists");
        formElementRelation
                .insert(new FormElement(name, serviceName, providerName, type, info));
        return new Response(true, "Form element added successfully");
    }
}
