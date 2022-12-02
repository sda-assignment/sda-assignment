package payments.strategy;

import payments.common.Response;
import payments.controllers.LogInSession;
import payments.entities.User;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;

public class PayViaWallet {
    private LogInSession current;
    double amount;
    Relation<User> target;

    public PayViaWallet(LogInSession U, double A, Relation<User> T) {
        current = U;
        amount = A;
        target = T;
    }

    public Response Pay() throws EntitySaveException {
        User active = current.getLoggedInUser();
        target.update(u -> new User(u.email, u.username, u.password, u.isAdmin, (u.wallet - amount)),
                u -> u.email.equals(active.email));
        return new Response(true, "Paid " + amount + " $ Via Wallet, Remaining balance = " + active.wallet + " \n");
    }
}
