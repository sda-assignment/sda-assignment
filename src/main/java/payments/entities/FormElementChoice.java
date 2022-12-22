package payments.entities;

import common.Util;

public class FormElementChoice {
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
