package payments.controllers.request;

public class LogInBody {
    public final String email;
    public final String password;

    public LogInBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
