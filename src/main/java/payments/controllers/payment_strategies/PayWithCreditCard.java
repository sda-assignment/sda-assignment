package payments.controllers.payment_strategies;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import common.Util;

public class PayWithCreditCard implements PaymentStrategy {
    private String cardNumber;

    public PayWithCreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void pay(double amount) {
        System.out.println("[DEBUG]: Paying using credit card: " + cardNumber);
        if (!Util.isPositiveInt(cardNumber))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card number");
    }
}
