package payments.controllers;

import java.util.ArrayList;

import datastore.Model;
import payments.entities.FormElementChoice;

public class FormElementController {
    private Model<FormElementChoice> formElementChoiceModel;

    public FormElementController(Model<FormElementChoice> formElementChoiceModel) {
        this.formElementChoiceModel = formElementChoiceModel;
    }

    public ArrayList<FormElementChoice> getChoicesForFormElement(String serviceName, String providerName) {
        return formElementChoiceModel
                .select(fec -> fec.serviceName.equals(serviceName) && fec.providerName.equals(providerName));
    }
}
