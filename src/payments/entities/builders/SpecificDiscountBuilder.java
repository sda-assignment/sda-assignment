package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.SpecificDiscount;

public class SpecificDiscountBuilder implements EntityBuilder<SpecificDiscount> {
    public SpecificDiscount fromString(String specificDiscount) {
        String[] splitted = specificDiscount.split(":");
        int i = 0;
        return new SpecificDiscount(Integer.parseInt(splitted[i++]), splitted[i++], splitted[i++]);
    }
}
