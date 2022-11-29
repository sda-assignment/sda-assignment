package payments.controllers;

import java.util.ArrayList;
import payments.entities.Discount;
import datastore.Relation;

public class UserDiscountController {
    private Relation<Discount> relation;

    public UserDiscountController(Relation<Discount> relation) {
        this.relation = relation;
    }

    public ArrayList<Discount> getDiscounts() {
        ArrayList<Discount> array = relation.select(d -> true);
        return array;
    }

}