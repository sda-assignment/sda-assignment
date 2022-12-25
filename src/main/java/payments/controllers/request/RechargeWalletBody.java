package payments.controllers.request;

public class RechargeWalletBody {
    public final double amount;
    public final String cardNumber;

    public RechargeWalletBody(double amount, String cardNumber) {
        this.amount = amount;
        this.cardNumber = cardNumber;
    }
}
