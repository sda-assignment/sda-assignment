package payments.entities.builders;

import datastore.EntityBuilder;
import payments.common.enums.DiscountType;
import payments.entities.Discount;

public class DiscountBuilder implements EntityBuilder<Discount> {
    public Discount fromString(String discount) {
        String[] splitted = discount.split(":");
        int i = 0;
        return new Discount(Integer.parseInt(splitted[i++]), DiscountType.valueOf(splitted[i++]),
                Double.parseDouble(splitted[i++]));
    }
}
