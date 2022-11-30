package payments.controllers;

import datastore.exceptions.EntitySaveException;

import payments.entities.Discount;
import payments.entities.enums.DiscountType;
import datastore.Relation;

public class AdminDiscountController {
    private Relation<Discount> relation;

    public AdminDiscountController(Relation<Discount> relation) {
        this.relation = relation;
    }

    public void addDiscount(int id, DiscountType discountType, String serviceName, double percentage)
            throws EntitySaveException {
        relation.insert(new Discount(id, discountType, serviceName, percentage));
    }

}
