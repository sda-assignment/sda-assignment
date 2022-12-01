package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.SpecificDiscount;

public class SpecificDiscountBuilder implements EntityBuilder<SpecificDiscount> {
    public SpecificDiscount fromString(String specificDiscount) {
        String[] splitted = specificDiscount.split(":");
        return new SpecificDiscount(Integer.parseInt(splitted[0]), splitted[1], splitted[2]);
    }
}
