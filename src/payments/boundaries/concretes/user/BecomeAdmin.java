package payments.boundaries.concretes.user;

import java.util.Scanner;

import datastore.exceptions.EntityException;
import payments.controllers.AuthController;
import payments.controllers.admin.AdminUserController;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;

//rechargeWallet(double amount, String cardNumber)

public class BecomeAdmin extends Frame {
    private AdminUserController adminUserController;
    private AuthController authController;

    public BecomeAdmin(AdminUserController adminUserController, AuthController authController) {
        this.adminUserController = adminUserController;
        this.authController = authController;
    }

    public FrameName getFrameName() {
        return FrameName.BECOME_ADMIN;
    }

    @Override
    protected FrameName display(Scanner input) throws EntityException {
        System.out.println("Do you want to become an admin?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        String option = input.nextLine();
        if (option.equals("1")) {
            adminUserController.setAdmin(authController.getLoggedInUser().email);
            return FrameName.HOME_ADMIN;
        } else {
            return FrameName.HOME_USER;
        }
    }

}
