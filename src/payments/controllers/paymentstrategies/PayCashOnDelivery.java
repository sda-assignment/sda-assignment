package payments.controllers.paymentstrategies;

import payments.common.Response;

public class PayCashOnDelivery implements PaymentStrategy {
    public Response pay(double amount) {
        System.out.println("[DEBUG]: Paying cash on delivery");
        return new Response(true, "Payment successful");
    }
}
