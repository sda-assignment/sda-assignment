package payments.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import payments.entities.Discount;
import payments.entities.UsedDiscount;
import datastore.Relation;

public class UserDiscountController {
    private Relation<Discount> discountRelation;
    private Relation<UsedDiscount> usedDiscountRelation;

    public UserDiscountController(Relation<Discount> relation) {
        this.discountRelation = relation;
    }

    public ArrayList<Discount> getDiscounts(String userEmail) {
        ArrayList<Discount> allDiscounts = discountRelation.select(d -> true);
        Collection<UsedDiscount> usedDiscounts = usedDiscountRelation.select(d -> d.email == userEmail);
        Set<Integer> usedDiscountsIds = new HashSet<Integer>();
        for (UsedDiscount d : usedDiscounts) {
            usedDiscountsIds.add(d.discountId);
        }
        allDiscounts.removeIf(d -> usedDiscountsIds.contains(d.id));
        return allDiscounts;
    }
}
