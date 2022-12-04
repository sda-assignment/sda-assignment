package payments.boundaries.concretes.user;

import java.util.ArrayList;

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

    public FrameName display() {
        for (int i = 1; i < providers.size(); ++i) {
            System.out.println(i + ". " + providers.get(i).name + " " + providers.get(i).serviceName + " service");
        }
        return FrameName.ERROR;
    }

}