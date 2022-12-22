package payments.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import payments.common.Response;
import payments.common.enums.DiscountType;
import payments.entities.Discount;
import payments.entities.UsedDiscount;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;

public class DiscountController {
    private Relation<Discount> discountRelation;
    private Relation<UsedDiscount> usedDiscountRelation;
    private LogInSession logInSession;

    public DiscountController(Relation<Discount> relation, Relation<UsedDiscount> usedDiscountRelation,
            LogInSession logInSession) {
        this.discountRelation = relation;
        this.usedDiscountRelation = usedDiscountRelation;
        this.logInSession = logInSession;
    }

    private ArrayList<Discount> getEffectiveDiscounts(ArrayList<Discount> discounts) {
        Collection<UsedDiscount> usedDiscounts = usedDiscountRelation
                .select(d -> d.email.equals(logInSession.getLoggedInUser().email));
        Set<Integer> usedDiscountsIds = new HashSet<Integer>();
        for (UsedDiscount d : usedDiscounts) {
            usedDiscountsIds.add(d.discountId);
        }
        discounts.removeIf(d -> usedDiscountsIds.contains(d.id));
        return discounts;
    }

    public ArrayList<Discount> getDiscountsForService(String serviceName) {
        ArrayList<Discount> discounts = discountRelation.select(d -> d.type == DiscountType.OVERALL
                || (d.type == DiscountType.SPECIFIC && d.serviceName.equals(serviceName)));
        return getEffectiveDiscounts(discounts);
    }

    public ArrayList<Discount> getAllDiscounts() {
        ArrayList<Discount> discounts = discountRelation.select(d -> true);
        return getEffectiveDiscounts(discounts);
    }

    public Response useDiscount(int discountId) throws EntitySaveException {
        usedDiscountRelation.insert(new UsedDiscount(logInSession.getLoggedInUser().email, discountId));
        return new Response(true, "Discount used successfully");
    }
}
