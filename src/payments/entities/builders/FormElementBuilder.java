package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.FormElement;
import payments.entities.enums.FormElementType;

public class FormElementBuilder implements EntityBuilder<FormElement> {
    public FormElement fromString(String formElement) {
        String[] splitted = formElement.split(":");
        int i = 0;
        return new FormElement(splitted[i++], splitted[i++], splitted[i++], FormElementType.valueOf(splitted[i++]),
                splitted[i++]);
    }
}
