package payments.entities;

import datastore.Entity;
import payments.entities.enums.DiscountType;

public class Discount implements Entity {
    public final int id;
    public final DiscountType type;
    public final String serviceName;
    public final double percentage;

    public Discount(int id, DiscountType type, String serviceName, double percentage) {
        this.id = id;
        this.type = type;
        this.serviceName = serviceName;
        this.percentage = percentage;

    }

    public String storify() {
        return id + ":" + type + ":" + serviceName + ":" + percentage;
    }
}
