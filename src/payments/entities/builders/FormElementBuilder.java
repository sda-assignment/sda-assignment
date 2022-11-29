package payments.entities.builders;

import datastore.EntityBuilder;
import payments.Util;
import payments.entities.FormElement;
import payments.entities.enums.FormElementType;

public class FormElementBuilder implements EntityBuilder<FormElement> {
    public FormElement fromString(String formElement) {
        String[] splitted = formElement.split(":");
        return new FormElement(splitted[0], splitted[1], splitted[2], FormElementType.valueOf(splitted[3]), splitted[4],
                Util.stringIsTrue(splitted[5]));
    }
}
