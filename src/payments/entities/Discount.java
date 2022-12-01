package payments.entities;

import datastore.Entity;
import payments.Util;
import payments.entities.enums.DiscountType;

public class Discount implements Entity {
    public final int id;
    public final DiscountType type;
    public final double percentage;

    public Discount(int id, DiscountType type, double percentage) {
        this.id = id;
        this.type = type;
        this.percentage = percentage;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] {id, type,  percentage});
    }
}
