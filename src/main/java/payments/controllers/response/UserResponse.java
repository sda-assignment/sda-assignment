package payments.controllers.response;

public class UserResponse {
    public final String email;
    public final String username;
    public final boolean isAdmin;
    public final double wallet;

    public UserResponse(String email, String username, boolean isAdmin, double wallet) {
        this.email = email;
        this.username = username;
        this.isAdmin = isAdmin;
        this.wallet = wallet;
    }
}
