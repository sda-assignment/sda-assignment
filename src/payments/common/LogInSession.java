package payments.common;

import payments.entities.User;



public class LogInSession {
    private LogInSession instance ;
    public User LoggedInUser;

    private LogInSession() {}


    public LogInSession getInstance(){
        if (instance == null) {
            instance = new LogInSession();
        }
        return instance;
    }
}

