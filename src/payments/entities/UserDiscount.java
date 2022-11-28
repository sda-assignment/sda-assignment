package payments.entities;

import datastore.Entity;

public class UserDiscount implements Entity {
    public final String email;
    public final int discountId;
    public final boolean isActive;

    public UserDiscount(String email, int discountId, boolean isActive) {
        this.email = email;
        this.discountId = discountId;
        this.isActive = isActive;
    }

    public String storify() {
        return email + ":" + discountId + ":" + isActive;
    }
}
