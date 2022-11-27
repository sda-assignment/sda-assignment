package payments.entities.builders;

import datastore.DataStoreObjectBuilder;
import payments.entities.User;

public class UserRecordBuilder implements DataStoreObjectBuilder<User> {
    public User fromString(String user) {
        String[] splitted = user.split(":");
        return new User(splitted[0], splitted[1], splitted[2]);
    }
}
