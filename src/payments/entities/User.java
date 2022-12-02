package payments.entities;

import common.Util;
import datastore.Entity;

public class User implements Entity {
    public final String email;
    public final String username;
    public final String password;
    public final boolean isAdmin;
    public final double wallet;

    public User(String email, String username, String password, boolean isStaff, double wallet) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isAdmin = isStaff;
        this.wallet = wallet;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] { email, username, password, isAdmin, wallet });
    }
}
