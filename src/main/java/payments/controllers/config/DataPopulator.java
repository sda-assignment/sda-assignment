package payments.controllers.config;

import java.util.ArrayList;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import datastore.Model;
import handlers.Handler;
import handlers.HandlerFactory;
import handlers.HandlerName;
import payments.common.enums.FormElementType;
import payments.entities.FormElement;
import payments.entities.Provider;
import payments.entities.Service;

@Component
public class DataPopulator implements ApplicationRunner {
    private Model<Service> serviceModel;
    private Model<Provider> providerModel;
    private Model<FormElement> formElementModel;

    public DataPopulator(Model<Service> serviceModel, Model<Provider> providerModel,
            Model<FormElement> formElementModel) {
        this.serviceModel = serviceModel;
        this.providerModel = providerModel;
        this.formElementModel = formElementModel;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String serviceName : new String[] { "mobile", "internet",
                "landline", "donation" }) {
            serviceModel.insert(new Service(serviceName));
        }
        providerModel.insert(new Provider("mobile", "orange", false, HandlerName.ORANGE_RECHARGE));
        providerModel.insert(new Provider("mobile", "we", false, HandlerName.WE_RECHARGE));
        providerModel
                .insert(new Provider("mobile", "etisalat", false, HandlerName.ETISALAT_RECHARGE));
        providerModel
                .insert(new Provider("mobile", "vodafone", false, HandlerName.VODAFONE_RECHARGE));

        providerModel.insert(new Provider("internet", "orange", false, HandlerName.ORANGE_INTERNET));
        providerModel.insert(new Provider("internet", "we", false, HandlerName.WE_INTERNET));
        providerModel
                .insert(new Provider("internet", "etisalat", false, HandlerName.ETISALAT_INTERNET));
        providerModel
                .insert(new Provider("internet", "vodafone", false, HandlerName.VODAFONE_INTERNET));

        providerModel.insert(new Provider("landline", "monthly", false, HandlerName.MONTHLY_LANDLINE));
        providerModel.insert(new Provider("landline", "quarterly", false, HandlerName.QUARTERLY_LANDLINE));

        providerModel
                .insert(new Provider("donation", "cancer", true, HandlerName.CANCER_HOSPITAL_DONATION));
        providerModel.insert(new Provider("donation", "schools", true, HandlerName.SCHOOL_DONATION));
        providerModel.insert(new Provider("donation", "ngo", true, HandlerName.NGO_DONATION));

        HandlerFactory handlerFactory = new HandlerFactory();
        ArrayList<Provider> allProviders = providerModel.select(p -> true);
        for (Provider provider : allProviders) {
            Handler handler = handlerFactory.getHandler(provider.handlerName);
            String[] requestKeys = handler.getRequestKeys();
            for (String key : requestKeys) {
                String constraints = handler.getConstraints();
                String info = key;
                if (constraints.toLowerCase().contains(key.toLowerCase())) {
                    info += " (" + constraints + ")";
                }
                formElementModel.insert(new FormElement(key, provider.serviceName, provider.name,
                        FormElementType.TEXT_FIELD, info));
            }
        }

    }
}
