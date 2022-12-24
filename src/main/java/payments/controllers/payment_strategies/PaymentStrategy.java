package payments.controllers.payment_strategies;

import payments.common.Response;

public interface PaymentStrategy {
    Response pay(double amount);
}
