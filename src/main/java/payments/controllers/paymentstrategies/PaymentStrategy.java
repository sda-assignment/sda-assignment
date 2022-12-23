package payments.controllers.paymentstrategies;

import payments.common.Response;

public interface PaymentStrategy {
    Response pay(double amount);
}
