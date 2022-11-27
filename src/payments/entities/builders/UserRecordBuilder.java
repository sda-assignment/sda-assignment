package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.User;

public class UserRecordBuilder implements EntityBuilder<User> {
    public User fromString(String user) {
        String[] splitted = user.split(":");
        return new User(splitted[0], splitted[1], splitted[2]);
    }
}
