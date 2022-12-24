package payments.controllers.request;

public class SignUpBody {
    public final String username;
    public final String email;
    public final String password;

    public SignUpBody(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
