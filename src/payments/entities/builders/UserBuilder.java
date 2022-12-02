package payments.entities.builders;

import common.Util;
import datastore.EntityBuilder;
import payments.entities.User;

public class UserBuilder implements EntityBuilder<User> {
    public User fromString(String user) {
        String[] splitted = user.split(":");
        int i = 0;
        return new User(splitted[i++], splitted[i++], splitted[i++], Util.stringToBoolean(splitted[i++]),
                Double.parseDouble(splitted[i++]));
    }
}
