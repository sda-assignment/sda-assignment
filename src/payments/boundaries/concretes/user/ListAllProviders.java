package payments.boundaries.concretes.user;

import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.controllers.ProviderController;
import payments.entities.Provider;
import java.util.ArrayList;

public class ListAllProviders extends Frame {

    private ProviderController providerController;

    public ListAllProviders(ProviderController providerController) {
        this.providerController = providerController;
    }

    public FrameName getFrameName() {
        return FrameName.LIST_ALL_PROVIDERS;
    }

    @Override
    protected FrameName display() throws EntityException {
        System.out.print("Providers");
        System.out.println("avaliable providers : ");
        ArrayList<Provider> providers = providerController.getAllProviders();
        ListProviders listProviders = new ListProviders(providers);
        listProviders.displayWithInstruction();
        return FrameName.HOME_USER;

    }

}
