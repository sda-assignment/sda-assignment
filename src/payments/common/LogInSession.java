package payments.common;

import payments.entities.User;



public class LogInSession {
    private static LogInSession instance ;
    public User LoggedInUser;

    private LogInSession() {
        LoggedInUser = null;
    }


    public static LogInSession getInstance(){
        if (instance == null) {
            instance = new LogInSession();
        }
        return instance;
    }

    public void setInstance(User LoggedInUser){
        this.LoggedInUser = LoggedInUser;
    }
}

