package payments.controllers.payment_strategies;

import datastore.Model;
import payments.common.Response;
import payments.entities.User;

public class PayWithWallet implements PaymentStrategy {
    private User payingUser;
    private Model<User> userModel;

    public PayWithWallet(Model<User> userModel, User payingUser) {
        this.userModel = userModel;
        this.payingUser = payingUser;
    }

    public Response pay(double amount) {
        if (payingUser.wallet < amount)
            return new Response(false, "You do not have enough money in the wallet for this payment");
        System.out.println("[DEBUG]: Paying with wallet");
        userModel.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet - amount),
                u -> u.email.equals(payingUser.email));
        return new Response(true, "Payment successful");
    }
}
