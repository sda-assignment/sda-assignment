package payments.boundaries;

import payments.boundaries.EnumViews.FrameName;
import payments.controllers.AuthController;

import java.util.Scanner;

public class GuestView extends Frame {

    String option;
    AuthController controller;

    public GuestView(AuthController controller) {
        this.controller = controller;
        frameName = "Guest";
    }

    public FrameName display() { 
        System.out.println("Guest \n");
        System.out.println("1- Sign In \n 2- Sign Up");
        Scanner userInput = new Scanner(System.in);
        String option = userInput.nextLine();
        userInput.close();
        if (option.equals("1")) {
            return FrameName.signIn;
        } else if (option.equals("2")) {
            return FrameName.signUp;
        } else if (option.equals("#")) {
            // a view that handles user error and returns him to same screen he was in
            // before error
            return FrameName.guestView;
        } else {
            return FrameName.error;
        }
    }

}
