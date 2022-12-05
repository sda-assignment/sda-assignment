package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import payments.entities.FormElementChoice;

public class FormElementController {
    private Relation<FormElementChoice> formElementChoiceRelation;

    public FormElementController(Relation<FormElementChoice> formElementChoiceRelation) {
        this.formElementChoiceRelation = formElementChoiceRelation;
    }

    public ArrayList<FormElementChoice> getChoicesForFormElement(String serviceName, String providerName) {
        return formElementChoiceRelation
                .select(fec -> fec.serviceName.equals(serviceName) && fec.providerName.equals(providerName));
    }
}
