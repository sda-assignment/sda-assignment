package payments.controllers.payment_strategies;

public interface PaymentStrategy {
    void pay(double amount);
}
