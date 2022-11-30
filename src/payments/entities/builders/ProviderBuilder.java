package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.Provider;
import payments.entities.enums.ProviderSocketName;
import payments.Util;

public class ProviderBuilder implements EntityBuilder<Provider> {
    public Provider fromString(String provider) {
        String[] splitted = provider.split(":");
        return new Provider(splitted[0], splitted[1], ProviderSocketName.valueOf(splitted[2]),
                Util.stringToBoolean(splitted[3]));
    }
}
