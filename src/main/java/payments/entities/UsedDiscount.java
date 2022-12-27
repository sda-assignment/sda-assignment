package payments.entities;

public class UsedDiscount {
    public final String email;
    public final int discountId;

    public UsedDiscount(String email, int discountId) {
        this.email = email;
        this.discountId = discountId;
    }
}
