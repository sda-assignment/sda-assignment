package payments.strategy;

import payments.common.Response;

public class PayViaCreditCard implements PaymentStrategy {
    double amount;

    public PayViaCreditCard(double A) {
        amount = A;
    }

    public Response Pay() {
        return new Response(true, "payed " + amount + "$ using Credit Card\n");
    }

}
