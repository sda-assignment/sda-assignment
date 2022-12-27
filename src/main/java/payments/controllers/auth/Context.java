package payments.controllers.auth;

public class Context {
    public final String email;
    public final boolean isAdmin;

    public Context(String email, boolean isAdmin) {
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
