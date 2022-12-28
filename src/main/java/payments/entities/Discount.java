package payments.entities;

import payments.enums.DiscountType;

public class Discount {
    public final int id;
    public final DiscountType type;
    public final double percentage;
    public final String serviceName;
    public final boolean isActive;

    public Discount(int id, DiscountType type, double percentage, String serviceName, boolean isActive) {
        this.id = id;
        this.type = type;
        this.percentage = percentage;
        this.serviceName = serviceName;
        this.isActive = isActive;
    }
}
