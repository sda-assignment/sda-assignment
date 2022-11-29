package payments.controllers;

import datastore.exceptions.EntitySaveException;

import payments.entities.Discount;
import datastore.Relation;

public class AdminDiscountController {
    private Relation<Discount> relation;

    public AdminDiscountController(Relation<Discount> relation) {
        this.relation = relation;
    }

    public void AddDiscount(Discount Obj) throws EntitySaveException {
        try {
            relation.insert(Obj);
        } catch (Exception e) {
            throw new EntitySaveException("Failed to Add Discount : " + e.toString());
        }

    }

}