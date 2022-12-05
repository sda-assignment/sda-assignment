package payments.boundaries.concretes.user;

import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.controllers.ProviderController;
import payments.entities.Provider;
import java.util.ArrayList;
import java.util.Scanner;

public class ListAllProviders extends Frame {

    private ProviderController providerController;

    public ListAllProviders(ProviderController providerController) {
        this.providerController = providerController;
    }

    public FrameName getFrameName() {
        return FrameName.LIST_ALL_PROVIDERS;
    }

    @Override
    protected FrameName display(Scanner input) throws EntityException {
        System.out.format("%15s","Providers \n");
        System.out.println("avaliable providers: ");
        ArrayList<Provider> providers = providerController.getAllProviders();
        ListProviders listProviders = new ListProviders(providers);
        listProviders.displayWithInstruction(input);
        System.out.println("enter search: ");
        String option = input.nextLine();
        if (option.equals("#")) {
            return FrameName.HOME_USER;
        }
        ArrayList<Provider> filteredProviders = providerController.searchForProviders(option);
        ListProviders filteredListProviders = new ListProviders(filteredProviders);
        filteredListProviders.displayWithInstruction(input);
        return FrameName.HOME_USER;
    }

}
