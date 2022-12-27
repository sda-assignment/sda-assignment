package payments.entities;

public class FormElementChoice {
    public final String info;
    public final String formElementName;
    public final String serviceName;
    public final String providerName;

    public FormElementChoice(String info, String formElementName, String serviceName, String providerName) {
        this.info = info;
        this.formElementName = formElementName;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }
}
