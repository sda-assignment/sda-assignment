package payments.boundaries.concretes.common;

import java.util.Scanner;

import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.common.Response;
import payments.controllers.AuthController;

public class SignUp extends Frame {

    private AuthController controller;

    public SignUp(AuthController controller) {
        this.controller = controller;
    }

    public FrameName getFrameName() {
        return FrameName.SIGN_UP;
    }

    @Override
    protected FrameName display(Scanner input) throws EntityException {

        System.out.format("%15s", "Sign up \n");
        System.out.println("email : ");
        String email = input.nextLine();

        if (email.equals("#")) {
            return FrameName.GUEST_VIEW;
        }

        System.out.println("username : ");
        String username = input.nextLine();
        System.out.println("password : ");
        String password = input.nextLine();

        Response result = controller.signUp(email, username, password);
        System.out.println(result.value);

        if (result.success) {
            return FrameName.BECOME_ADMIN;
        } else {
            System.out.println(result.value);
            return FrameName.SIGN_UP;
        }
    }

}
