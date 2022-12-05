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
    protected FrameName display() throws EntityException {
        System.out.format("%15s","Providers \n");
        System.out.println("avaliable providers : ");
        ArrayList<Provider> providers = providerController.getAllProviders();
        ListProviders listProviders = new ListProviders(providers);
        listProviders.displayWithInstruction();
        System.out.println("enter search : ");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        input.close();
        if (option.equals("#")) {
            return FrameName.HOME_USER;
        }
        ArrayList<Provider> filteredProviders = providerController.searchForProviders(option);
        ListProviders filteredListProviders = new ListProviders(filteredProviders);
        filteredListProviders.displayWithInstruction();
        return FrameName.HOME_USER;
    }

}
