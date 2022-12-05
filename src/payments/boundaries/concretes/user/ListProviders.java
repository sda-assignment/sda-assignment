package payments.boundaries.concretes.user;

import java.util.ArrayList;
import java.util.Scanner;

import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.entities.Provider;

public class ListProviders extends Frame {
    private ArrayList<Provider> providers;

    public ListProviders(ArrayList<Provider> providers) {
        this.providers = providers;
    }

    public FrameName getFrameName() {
        return FrameName.LIST_PROVIDERS;
    }

    public FrameName display(Scanner input) {
        for (int i = 0; i < providers.size(); ++i) {
            System.out.println(i + ". Provider Name: " + providers.get(i).name + " - Service Name: "
                    + providers.get(i).serviceName);
        }
        return FrameName.ERROR;
    }

}
