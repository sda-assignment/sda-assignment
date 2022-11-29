package payments.entities;

import datastore.Entity;

public class UsedDiscount implements Entity {
    public final String email;
    public final int discountId;

    public UsedDiscount(String email, int discountId) {
        this.email = email;
        this.discountId = discountId;
    }

    public String storify() {
        return email + ":" + discountId;
    }
}
