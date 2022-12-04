package payments.entities;

import common.Util;
import datastore.Entity;
import payments.common.enums.DiscountType;

public class Discount implements Entity {
    public final int id;
    public final DiscountType type;
    public final double percentage;
    public final String serviceName;

    public Discount(int id, DiscountType type, double percentage, String serviceName) {
        this.id = id;
        this.type = type;
        this.percentage = percentage;
        this.serviceName = serviceName;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] { id, type, percentage, serviceName });
    }
}
