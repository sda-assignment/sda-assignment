package payments.common;

public class LogInSession {
    private LogInSession LoggedInUser = new LogInSession();

    private LogInSession(){}

    public LogInSession getInstance(){
        return LoggedInUser;
    }
}
