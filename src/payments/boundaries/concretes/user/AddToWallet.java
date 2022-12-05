package payments.boundaries.concretes.user;

import java.util.Scanner;

import common.Util;
import datastore.exceptions.EntityException;
import payments.common.Response;
import payments.controllers.UserController;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;

//rechargeWallet(double amount, String cardNumber)

public class AddToWallet extends Frame {
    private UserController userController;

    public AddToWallet(UserController userController) {
        this.userController = userController;
    }

    public FrameName getFrameName() {
        return FrameName.ADD_AMOUNT_TO_WALLET;
    }

    @Override
    protected FrameName display(Scanner input) throws EntityException {
        System.out.format("%15s", "Add Amount To Wallet \n");
        System.out.println("credit card: ");
        String creditCard = input.nextLine();
        if (creditCard.equals("#")) {
            return FrameName.HOME_USER;
        }

        System.out.println("amount: ");
        String amount = input.nextLine();

        if (!Util.isPositiveFloat(amount)) {
            System.out.println("Please enter a valid amount");
            return FrameName.ADD_AMOUNT_TO_WALLET;
        }

        Response response = userController.rechargeWallet(Double.parseDouble(amount), creditCard);

        System.out.println(response.value);
        return FrameName.ADD_AMOUNT_TO_WALLET;
    }

}
