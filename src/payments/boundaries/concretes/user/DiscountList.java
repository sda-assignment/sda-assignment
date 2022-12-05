package payments.boundaries.concretes.user;

import java.util.ArrayList;
import java.util.Scanner;

import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.controllers.DiscountController;
import payments.entities.Discount;

public class DiscountList extends Frame {
    private DiscountController discountController;

    public DiscountList(DiscountController discountController) {
        this.discountController = discountController;
    }

    public FrameName getFrameName() {
        return FrameName.DISCOUNT;
    }

    @Override
    protected FrameName display(Scanner input) throws EntityException {

        ArrayList<Discount> array = discountController.getAllDiscounts();
        System.out.format("%15s", "Current discounts \n");
        for (Discount element : array) {
            System.out.println("id: " + element.id + " - type: " + element.type + " - percentage: " + element.percentage
                    + " - serviceName: " + element.serviceName);
        }

        System.out.println("choice (you can only go back, press anything): ");
        input.nextLine();
        return FrameName.HOME_USER;

    }

}
