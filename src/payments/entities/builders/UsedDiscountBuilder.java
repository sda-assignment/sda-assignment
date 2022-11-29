package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.UsedDiscount;

public class UsedDiscountBuilder implements EntityBuilder<UsedDiscount> {
    public UsedDiscount fromString(String usedDiscount) {
        String[] splitted = usedDiscount.split(":");
        return new UsedDiscount(splitted[0], Integer.parseInt(splitted[1]));
    }
}
