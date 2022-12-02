package payments.strategy;

import payments.common.Response;

public class PayViaCashOnDelivery implements PaymentStrategy {
    double amount;

    public PayViaCashOnDelivery(double A) {
        amount = A;
    }

    public Response Pay() {
        return new Response(true, "payed " + amount + "$ using Cash\n");
    }

}
