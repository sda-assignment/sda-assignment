package payments.controllers;

import java.util.ArrayList;

import payments.common.LogInSession;
import payments.common.Response;
import datastore.exceptions.EntitySaveException;
import payments.entities.User;
import datastore.Relation;

public class AuthController {
    private Relation<User> relation;
    private LogInSession logInSession;

    public AuthController(Relation<User> relation, LogInSession logInSession) {
        this.relation = relation;
        this.logInSession = logInSession;
    }

    public Response signUp(String email, String userName, String password) throws EntitySaveException {
        ArrayList<User> temp = relation.select(u -> u.email.equals(email));

        if (temp.size() == 0) {
            relation.insert(new User(email, userName, password, false, 0));
            logInSession.setLoggedInUser((new User(email, userName, password, false, 0)));

            return new Response(true, "Signed up successfully");
        }

        else {
            return new Response(false, "User already exists");
        }
    }

    public Response logIn(String email, String password) {
        ArrayList<User> users = relation.select(u -> u.email.equals(email) && u.password.equals(password));
        if (users.size() >= 0) {
            logInSession.setLoggedInUser(users.get(0));
            return new Response(true, "Logged in Successfully");
        }
        return new Response(false, "Incorrect username or password");
    }

    public Response logOut() {
        logInSession.setLoggedInUser(null);
        return new Response(true, null);
    }
}
