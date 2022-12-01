package payments.controllers;

import datastore.exceptions.EntitySaveException;
import payments.Util;
import payments.entities.Discount;
import payments.entities.enums.DiscountType;
import datastore.Relation;

public class AdminDiscountController {
    private Relation<Discount> relation;

    public AdminDiscountController(Relation<Discount> relation) {
        this.relation = relation;
    }

    public void addDiscount(DiscountType discountType, String serviceName, double percentage)
            throws EntitySaveException {
        Integer id = Util.incrementOrInitialize(relation.selectMax(d -> d.id));
        relation.insert(new Discount(id, discountType, percentage));
    }

}
