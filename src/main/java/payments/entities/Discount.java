package payments.entities;

import payments.common.enums.DiscountType;

public class Discount {
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
}
