package payments.controllers.request;

public class LogInInfo {
    public final String email;
    public final String password;

    public LogInInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
