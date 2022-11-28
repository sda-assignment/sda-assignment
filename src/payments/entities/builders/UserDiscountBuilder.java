package payments.entities.builders;

import datastore.EntityBuilder;
import payments.Util;
import payments.entities.UserDiscount;

public class UserDiscountBuilder implements EntityBuilder<UserDiscount> {
    public UserDiscount fromString(String userDiscount) {
        String[] splitted = userDiscount.split(":");
        return new UserDiscount(splitted[0], Integer.parseInt(splitted[1]), Util.stringIsTrue(splitted[2]));
    }
}
