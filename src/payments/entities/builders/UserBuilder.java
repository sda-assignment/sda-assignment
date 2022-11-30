package payments.entities.builders;

import datastore.EntityBuilder;
import payments.Util;
import payments.entities.User;

public class UserBuilder implements EntityBuilder<User> {
    public User fromString(String user) {
        String[] splitted = user.split(":");
        return new User(splitted[0], splitted[1], splitted[2], Util.stringToBoolean(splitted[3]),
                Double.parseDouble(splitted[4]));
    }
}
