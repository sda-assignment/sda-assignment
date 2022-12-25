package payments.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import payments.common.enums.DiscountType;
import payments.controllers.auth.Context;
import payments.controllers.auth.TokenUtil;
import payments.entities.Discount;
import payments.entities.UsedDiscount;
import datastore.Model;

@RestController
public class DiscountController {
    private Model<Discount> discountModel;
    private Model<UsedDiscount> usedDiscountModel;
    private TokenUtil tokenUtil;

    public DiscountController(Model<Discount> model, Model<UsedDiscount> usedDiscountModel,
            TokenUtil tokenUtil) {
        this.discountModel = model;
        this.usedDiscountModel = usedDiscountModel;
        this.tokenUtil = tokenUtil;
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
        ArrayList<Discount> discounts = discountModel.select(d -> d.type == DiscountType.OVERALL
                || (d.type == DiscountType.SPECIFIC && d.serviceName.equals(serviceName)));
        return getEffectiveDiscountsForUser(email, discounts);
    }

    @GetMapping("/services/discounts/{serviceName}")
    public ArrayList<Discount> getDiscountsForService(@PathVariable("serviceName") String serviceName,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Context ctx = tokenUtil.getContextFromAuthHeader(authHeader);
        return getDiscountsForServiceForUser(ctx.email, serviceName);
    }

    @GetMapping("/services/discounts")
    public ArrayList<Discount> getAllDiscounts(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        ArrayList<Discount> discounts = discountModel.select(d -> true);
        Context ctx = tokenUtil.getContextFromAuthHeader(authHeader);
        return getEffectiveDiscountsForUser(ctx.email, discounts);
    }

    public boolean useDiscount(String email, int discountId) {
        usedDiscountModel.insert(new UsedDiscount(email, discountId));
        return true;
    }
}
