package payments.controllers.admin;

import payments.common.Response;
import payments.common.enums.DiscountType;
import payments.entities.Discount;
import common.Util;
import datastore.Model;

public class AdminDiscountController {
    private Model<Discount> discountModel;

    public AdminDiscountController(Model<Discount> model) {
        this.discountModel = model;
    }

    private void addDiscount(DiscountType discountType, double percentage, String serviceName) {
        Integer id = Util.incrementOrInitialize(discountModel.selectMax(d -> d.id));
        Discount discountToInsert = new Discount(id, discountType, percentage, serviceName);
        discountModel.insert(discountToInsert);
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
