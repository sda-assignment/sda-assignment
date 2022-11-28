package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.User;

public class UserBuilder implements EntityBuilder<User> {
    public User fromString(String user) {
        String[] splitted = user.split(":");
        return new User(splitted[0], splitted[1], splitted[2], splitted[3].equals("true"),
                Double.parseDouble(splitted[4]));
    }
}
