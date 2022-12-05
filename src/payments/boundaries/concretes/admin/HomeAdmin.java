package payments.boundaries.concretes.admin;

import java.util.Scanner;

import payments.controllers.AuthController;
import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.common.Response;

public class HomeAdmin extends Frame {

    private AuthController authcontroller;

    public HomeAdmin(AuthController authController) {
        this.authcontroller = authController;
    }

    @Override
    public FrameName getFrameName() {
        return FrameName.HOME_ADMIN;
    }

    protected FrameName display(Scanner input) throws EntityException {
        System.out.format("%15s", "HOME : Admin\n");
        System.out.println("1. Add a discount");
        System.out.println("2. List user transaction");
        System.out.println("3. View refund request");
        System.out.println("choice: ");

        String option = input.nextLine();

        if (option.equals("#")) {
            Response obj = authcontroller.logOut();
            System.out.println(obj.value);
            return FrameName.GUEST_VIEW;

        } else if (option.equals("1")) {
            return FrameName.ADD_DISCOUNT;

        } else if (option.equals("2")) {
            return FrameName.ADMIN_LIST_TRANSACTION;

        } else if (option.equals("3")) {
            return FrameName.ADMIN_REFUND;

        }

        System.out.println("Please entet a valid input");
        return FrameName.HOME_ADMIN;
    }
}
