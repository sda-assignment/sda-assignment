package payments.controllers;

import common.Util;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.Response;
import payments.entities.User;

public class UserController {
    private Relation<User> userRelation;
    private LogInSession logInSession;

    public UserController(Relation<User> userRelation, LogInSession logInSession) {
        this.userRelation = userRelation;
        this.logInSession = logInSession;
    }

    public Response rechargeWallet(double amount, String cardNumber) throws EntitySaveException {
        if (!Util.isPositiveInt(cardNumber))
            return new Response(false, "Invalid card number");
        if (amount < 0)
            return new Response(false, "Invalid amount");
        userRelation.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + amount),
                u -> u.email.equals(logInSession.getLoggedInUser().email));
        return new Response(true, "Wallet recharged successfully");
    }
}
