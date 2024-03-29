package payments.entities;

public class User {
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
}
