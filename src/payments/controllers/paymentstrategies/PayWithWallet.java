package payments.controllers.paymentstrategies;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.Response;
import payments.entities.User;

public class PayWithWallet implements PaymentStrategy {
    private User payingUser;
    private Relation<User> userRelation;

    public PayWithWallet(Relation<User> userRelation, User payingUser) {
        this.userRelation = userRelation;
        this.payingUser = payingUser;
    }

    public Response pay(double amount) throws EntitySaveException {
        if (payingUser.wallet < amount)
            return new Response(false, "You do not have enough money in the wallet for this payment");
        System.out.println("[DEBUG]: Paying with wallet");
        userRelation.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet - amount),
                u -> u.email.equals(payingUser.email));
        return new Response(true, "Payment successful");
    }
}
