package payments.controllers;

import payments.entities.User;

public class LogInSession {
    private User loggedInUser;

    public LogInSession() {
        this.loggedInUser = null;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
