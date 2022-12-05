package payments.entities;

import common.Util;
import datastore.Entity;

public class FormElementChoice implements Entity {
    public final String info;
    public final String serviceName;
    public final String providerName;

    public FormElementChoice(String info, String serviceName, String providerName) {
        this.info = info;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] { info, serviceName, providerName });
    }
}
