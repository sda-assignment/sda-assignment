package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.Discount;
import payments.entities.enums.DiscountType;

public class DiscountBuilder implements EntityBuilder<Discount> {
    public Discount fromString(String discount) {
        String[] splitted = discount.split(":");
        return new Discount(Integer.parseInt(splitted[0]), DiscountType.valueOf(splitted[1]),
                Double.parseDouble(splitted[2]));
    }
}
