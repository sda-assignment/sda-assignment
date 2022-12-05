package payments.boundaries.concretes.common;

import payments.boundaries.Frame;
import payments.boundaries.FrameName;

import java.util.Scanner;

public class GuestView extends Frame {
    public FrameName getFrameName() {
        return FrameName.GUEST_VIEW;
    }

    protected FrameName display(Scanner userInput) {
        System.out.format("%15s", "Guest \n");
        System.out.println("1- Sign In \n2- Sign Up");
        String option = userInput.nextLine();
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
