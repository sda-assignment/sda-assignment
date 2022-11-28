package payments.entities;

import datastore.Entity;

public class User implements Entity {
    public final String email;
    public final String username;
    public final String password;
    public final boolean isStaff;
    public final double wallet;

    public User(String email, String username, String password, boolean isStaff, double wallet) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isStaff = isStaff;
        this.wallet = wallet;
    }

    public String storify() {
        return email + ":" + username + ":" + password + ":" + isStaff + ":" + wallet;
    }
}
