package payments.boundaries.concretes.admin;

import java.util.ArrayList;
import java.util.Scanner;

import common.Util;
import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.common.Response;
import payments.controllers.ServiceController;
import payments.controllers.admin.AdminDiscountController;
import payments.entities.Service;

public class AdminAddDiscount extends Frame {
    private AdminDiscountController adminDiscountController;
    private ServiceController serviceController;

    public AdminAddDiscount(AdminDiscountController adminDiscountController, ServiceController serviceController) {
        this.adminDiscountController = adminDiscountController;
        this.serviceController = serviceController;
    }

    @Override
    public FrameName getFrameName() {
        return FrameName.ADD_DISCOUNT;
    }

    @Override
    protected FrameName display(Scanner input) throws EntityException {
        System.out.format("%15s", "Add A Discount");
        System.out.println("\nEnter Discount type");
        System.out.println("1- Overall \n2- Specific");
        System.out.println("choice : ");
        String option = input.nextLine();

        if (option.equals("#")) {
            return FrameName.HOME_ADMIN;
        }

        if (option.equals("1")) {
            System.out.format("%15s", "Overall discount ");
            System.out.println("\nEnter percentage: ");
            option = input.nextLine();
            while (!Util.isPositiveFloat(option)) {
                System.out.println("Please enter a valid percentage");
                option = input.nextLine();
            }
            double amount = Double.parseDouble(option);
            Response response = adminDiscountController.addOverallDiscount(amount);
            System.out.println(response.value);
            return FrameName.ADD_DISCOUNT;

        } else if (option.equals("2")) {
            System.out.format("%15s", "Specific discount ");
            ArrayList<Service> services = serviceController.getAllServices();
            int i = 1;
            for (Service element : services) {
                System.out.println(i + "- " + element.name);
                i++;
            }
            // is validation needed here ?
            System.out.println("\nEnter choice (service name): ");
            option = input.nextLine();
            if (!option.chars().allMatch(Character::isDigit)) {
                System.out.println("Please enter a valid amount ");
                option = input.nextLine();

            }
            String serviceName = option;
            System.out.println("Enter amount : ");
            option = input.nextLine();
            while (!Util.isPositiveFloat(option)) {
                System.out.println("Please enter a valid amount ");
                option = input.nextLine();
            }
            double amount = Double.parseDouble(option);
            Response response = adminDiscountController.addSpecificDiscount(serviceName, amount);
            System.out.println(response.value);
            return FrameName.ADD_DISCOUNT;
        } else {
            System.out.println("invalid input");
            return FrameName.ADD_DISCOUNT;

        }
    }

}
