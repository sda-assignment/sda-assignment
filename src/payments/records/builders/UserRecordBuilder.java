package payments.records.builders;

import datastore.DataStoreObjectBuilder;
import payments.records.User;

public class UserRecordBuilder implements DataStoreObjectBuilder {
    public User fromString(String user) {
        String[] splitted = user.split(":");
        return new User(splitted[0], splitted[1], splitted[2]);
    }
}
