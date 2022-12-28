package payments.controllers.admin;

import payments.common.enums.DiscountType;
import payments.controllers.ServiceController;
import payments.controllers.request.AddDiscountBody;
import payments.entities.Discount;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        Discount discountToInsert = new Discount(id, discountType, percentage, serviceName, true);
        discountModel.insert(discountToInsert);
    }

    @GetMapping("/admin/discounts")
    public ArrayList<Discount> listDiscounts() {
        return discountModel.select(d -> true);
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

    @PostMapping("/admin/discounts/{id}/deactivate")
    public void deactivateDiscount(@PathVariable("id") int id) {
        ArrayList<Discount> updated = discountModel.update(
                d -> new Discount(d.id, d.type, d.percentage, d.serviceName, false),
                d -> d.id == id && d.isActive);
        if (updated.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active discount with this id");
    }

    @PostMapping("/admin/discounts/{id}/activate")
    public void activateDiscount(@PathVariable("id") int id) {
        ArrayList<Discount> updated = discountModel.update(
                d -> new Discount(d.id, d.type, d.percentage, d.serviceName, true),
                d -> d.id == id && !d.isActive);
        if (updated.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an inactive discount with this id");

    }
}
