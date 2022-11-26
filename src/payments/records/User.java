package payments.records;

import datastore.DataStoreObject;

public class User implements DataStoreObject {
    public final String email;
    public final String username;
    public final String password;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String storify() {
        return email + ":" + username + ":" + password;
    }
}
