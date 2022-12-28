package payments.entities;

import payments.enums.FormElementType;

public class FormElement {
    public final String name;
    public final String serviceName;
    public final String providerName;
    public final FormElementType type;
    public final String info;

    public FormElement(String name, String serviceName, String providerName, FormElementType type, String info) {
        this.name = name;
        this.serviceName = serviceName;
        this.providerName = providerName;
        this.type = type;
        this.info = info;
    }
}
