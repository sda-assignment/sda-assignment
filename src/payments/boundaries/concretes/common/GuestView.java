package payments.boundaries.concretes.common;

import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.controllers.AuthController;

import java.util.Scanner;

public class GuestView extends Frame {

    String option;
    AuthController controller;

    public GuestView(AuthController controller) {
        this.controller = controller;
    }

    public FrameName getFrameName ()
    {
        return FrameName.GUEST_VIEW;
    }

    protected FrameName display() { 
        System.out.format("%15s","Guest \n");
        System.out.println("1- Sign In \n2- Sign Up");
        Scanner userInput = new Scanner(System.in);
        String option = userInput.nextLine();
        userInput.close();
        if (option.equals("1")) {
            return FrameName.SIGN_IN;
        } else if (option.equals("2")) {
            return FrameName.SIGN_UP;
        } else if (option.equals("#")) {
            return FrameName.GUEST_VIEW;
        } else {
            System.out.println("Please enter valid input ");
            return FrameName.GUEST_VIEW;
        }
    }

}
