package payments.entities;

import common.Util;
import datastore.Entity;

public class FormElementChoice implements Entity {
    private String info;
    private String serviceName;
    private String providerName;

    public FormElementChoice(String info, String serviceName, String providerName) {
        this.info = info;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] { info, serviceName, providerName });
    }
}
