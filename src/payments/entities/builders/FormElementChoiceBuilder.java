package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.FormElementChoice;

public class FormElementChoiceBuilder implements EntityBuilder<FormElementChoice> {
    public FormElementChoice fromString(String formElementChoice) {
        String[] splitted = formElementChoice.split(":");
        int i = 0;
        return new FormElementChoice(splitted[i++], splitted[i++], splitted[i++]);
    }
}
