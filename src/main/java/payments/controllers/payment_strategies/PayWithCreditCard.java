package payments.controllers.payment_strategies;

import common.Util;
import payments.common.Response;

public class PayWithCreditCard implements PaymentStrategy {
    private String cardNumber;

    public PayWithCreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Response pay(double amount) {
        System.out.println("[DEBUG]: Paying using credit card: " + cardNumber);
        if (!Util.isPositiveInt(cardNumber))
            return new Response(false, "Invalid card number");
        return new Response(true, "Payment successful");
    }
}
