package payments.entities;

import common.Util;
import datastore.Entity;
import payments.entities.enums.FormElementType;

public class FormElement implements Entity {
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

    public String storify() {
        return Util.separateWithColons(new Object[] { name, serviceName, providerName, type, info });
    }
}
