package payments.boundaries;

import java.util.Scanner;

import datastore.exceptions.EntityException;
import payments.boundaries.EnumViews.FrameName;
import payments.common.Response;
import payments.controllers.AuthController;

public class SignIn extends Frame {

    private AuthController controller;

    public SignIn(AuthController controller) {
        this.controller = controller;
        frameName = "signIn";
    }

    @Override
    public FrameName display() throws EntityException {
        Scanner input = new Scanner(System.in);
        System.out.println("Sign in \n");
        System.out.println("email : ");
        String email = input.nextLine();
        if (email.equals("#")) {
            input.close();
            return FrameName.guestView;
        }
        System.out.println("password : ");
        String password = input.nextLine();
        input.close();
        Response result = controller.logIn(email, password);
        System.out.println(result.value);
        if (result.success) {
            // how to know if home admin
            return FrameName.homeUser;
        } else {
            return FrameName.signIn;
        }
    }

}
