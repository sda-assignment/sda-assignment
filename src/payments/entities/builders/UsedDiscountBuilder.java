package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.UsedDiscount;

public class UsedDiscountBuilder implements EntityBuilder<UsedDiscount> {
    public UsedDiscount fromString(String usedDiscount) {
        String[] splitted = usedDiscount.split(":");
        int i = 0;
        return new UsedDiscount(splitted[i++], Integer.parseInt(splitted[i++]));
    }
}
