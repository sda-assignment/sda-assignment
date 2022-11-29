package payments.entities;

import datastore.Entity;
import payments.entities.enums.FormElementType;

public class FormElement implements Entity {
    public final String name;
    public final String serviceName;
    public final String providerName;
    public final FormElementType type;
    public final String info;
    public final boolean hasDeductionAmount;

    public FormElement(String name, String serviceName, String providerName, FormElementType type, String info,
            boolean hasDeductionAmount) {
        this.name = name;
        this.serviceName = serviceName;
        this.providerName = providerName;
        this.type = type;
        this.info = info;
        this.hasDeductionAmount = hasDeductionAmount;
    }

    public String storify() {
        return name + ":" + serviceName + ":" + providerName + ":" + type + ":" + info + ":" + hasDeductionAmount;
    }
}
