package payments.controllers.admin;

import payments.common.Response;
import payments.common.enums.DiscountType;
import payments.entities.Discount;
import common.Util;
import datastore.Relation;

public class AdminDiscountController {
    private Relation<Discount> discountRelation;

    public AdminDiscountController(Relation<Discount> relation) {
        this.discountRelation = relation;
    }

    private void addDiscount(DiscountType discountType, double percentage, String serviceName) {
        Integer id = Util.incrementOrInitialize(discountRelation.selectMax(d -> d.id));
        Discount discountToInsert = new Discount(id, discountType, percentage, serviceName);
        discountRelation.insert(discountToInsert);
    }

    public Response addOverallDiscount(double percentage) {
        addDiscount(DiscountType.OVERALL, percentage, "None");
        return new Response(true, "Discount added successfully");
    }

    public Response addSpecificDiscount(String serviceName, double percentage) {
        addDiscount(DiscountType.SPECIFIC, percentage, serviceName);
        return new Response(true, "Discount added successfully");
    }

}
