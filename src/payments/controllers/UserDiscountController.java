package payments.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import payments.entities.Discount;
import payments.entities.UsedDiscount;
import payments.common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;

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

    public Response addUsedDiscount(Discount record) throws EntitySaveException {
        discountRelation.insert(record);
        return new Response(true, "Discout successfully logged to used Discounts \n");
    }

    // returns maximum discount per service
    public Discount getMaxDiscount(String userEmail, String serviceName) {
        ArrayList<Discount> allDiscounts = getDiscounts(userEmail);
        Discount maxDiscount = new Discount(-1, null, userEmail, 0);
        for (Discount d : allDiscounts) {
            if (d.serviceName.equals(serviceName)) {
                if (d.percentage > maxDiscount.percentage) {
                    maxDiscount = d;
                }
            }

        }
        return maxDiscount;

    }

}
