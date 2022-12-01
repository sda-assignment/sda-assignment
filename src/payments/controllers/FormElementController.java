package payments.controllers;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.Response;
import payments.entities.FormElement;
import payments.entities.enums.FormElementType;

public class FormElementController {
    private Relation<FormElement> formElementRelation;

    public Response addFormElement(String name, String serviceName, String providerName, FormElementType type,
            String info,
            boolean hasDeductionAmount) throws EntitySaveException {
        if (formElementRelation.entityExists(
                fe -> fe.name == name && fe.serviceName == serviceName && fe.providerName == providerName))
            return new Response(false, "This form element already exists");
        if (hasDeductionAmount && formElementRelation.entityExists(
                fe -> fe.serviceName == serviceName && fe.providerName == providerName && fe.hasDeductionAmount))
            return new Response(false, "Only one form element can have the deduction amount");
        formElementRelation
                .insert(new FormElement(name, serviceName, providerName, type, info, hasDeductionAmount));
        return new Response(true, "Form element added successfully");
    }
}
