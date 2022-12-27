package payments.entities;

public class FormElementChoice {
    public final String info;
    public final String serviceName;
    public final String providerName;

    public FormElementChoice(String info, String serviceName, String providerName) {
        this.info = info;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }
}
