package payments.controllers.payment_strategies;

public class PayCashOnDelivery implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("[DEBUG]: Paying cash on delivery");
    }
}
