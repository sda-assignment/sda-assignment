package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.Provider;
import payments.entities.enums.HandlerName;
import payments.Util;

public class ProviderBuilder implements EntityBuilder<Provider> {
    public Provider fromString(String provider) {
        String[] splitted = provider.split(":");
        int i = 0;
        return new Provider(splitted[i++], splitted[i++], Util.stringToBoolean(splitted[i++]),
                HandlerName.valueOf(splitted[i++]));
    }
}
