package payments.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import payments.common.enums.DiscountType;
import payments.controllers.auth.Context;
import payments.entities.Discount;
import payments.entities.UsedDiscount;
import datastore.Model;

@RestController
public class DiscountController {
    private Model<Discount> discountModel;
    private Model<UsedDiscount> usedDiscountModel;

    public DiscountController(Model<Discount> model, Model<UsedDiscount> usedDiscountModel) {
        this.discountModel = model;
        this.usedDiscountModel = usedDiscountModel;
    }

    private ArrayList<Discount> getEffectiveDiscountsForUser(String email, ArrayList<Discount> discounts) {
        Collection<UsedDiscount> usedDiscounts = usedDiscountModel
                .select(d -> d.email.equals(email));
        Set<Integer> usedDiscountsIds = new HashSet<Integer>();
        for (UsedDiscount d : usedDiscounts) {
            usedDiscountsIds.add(d.discountId);
        }
        discounts.removeIf(d -> usedDiscountsIds.contains(d.id));
        return discounts;
    }

    public ArrayList<Discount> getDiscountsForServiceForUser(String email, String serviceName) {
        ArrayList<Discount> discounts = discountModel.select(d -> d.isActive && (d.type == DiscountType.OVERALL
                || (d.type == DiscountType.SPECIFIC && d.serviceName.equals(serviceName))));
        return getEffectiveDiscountsForUser(email, discounts);
    }

    @GetMapping("/discounts")
    public ArrayList<Discount> listDiscounts(@RequestAttribute("context") Context ctx,
            @RequestParam(required = false) String serviceName) {
        if (serviceName == null)
            return getEffectiveDiscountsForUser(ctx.email, discountModel.select(d -> d.isActive));
        return getDiscountsForServiceForUser(ctx.email, serviceName);
    }

    public boolean useDiscount(String email, int discountId) {
        usedDiscountModel.insert(new UsedDiscount(email, discountId));
        return true;
    }
}
