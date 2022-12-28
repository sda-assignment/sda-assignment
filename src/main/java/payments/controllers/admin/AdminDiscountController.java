package payments.controllers.admin;

import payments.common.enums.DiscountType;
import payments.controllers.ServiceController;
import payments.controllers.request.AddDiscountBody;
import payments.entities.Discount;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import common.Util;
import datastore.Model;

@RestController
public class AdminDiscountController {
    private Model<Discount> discountModel;
    private ServiceController serviceController;

    public AdminDiscountController(Model<Discount> model, ServiceController serviceController) {
        this.discountModel = model;
        this.serviceController = serviceController;
    }

    private void addDiscount(DiscountType discountType, double percentage, String serviceName) {
        Integer id = Util.incrementOrInitialize(discountModel.selectMax(d -> d.id));
        Discount discountToInsert = new Discount(id, discountType, percentage, serviceName);
        discountModel.insert(discountToInsert);
    }

    @PostMapping("/admin/discounts")
    public void addOverallDiscount(@RequestBody AddDiscountBody body) {
        addDiscount(DiscountType.OVERALL, body.percentage, "None");
    }

    @PostMapping("/admin/services/{serviceName}/discounts")
    public void addSpecificDiscount(@PathVariable("serviceName") String serviceName,
            @RequestBody AddDiscountBody body) {
        serviceController.getService(serviceName);
        addDiscount(DiscountType.SPECIFIC, body.percentage, serviceName);
    }

}
