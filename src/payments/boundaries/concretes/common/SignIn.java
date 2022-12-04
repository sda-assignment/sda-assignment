package payments.boundaries.concretes.common;

import java.util.Scanner;

import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.common.Response;
import payments.controllers.AuthController;

public class SignIn extends Frame {

    private AuthController controller;

    public SignIn(AuthController controller) {
        this.controller = controller;
    }

    public FrameName getFrameName ()
    {
        return FrameName.SIGN_IN;
    }

    @Override
    protected FrameName display() throws EntityException {
        Scanner input = new Scanner(System.in);
        System.out.println("Sign in \n");
        System.out.println("email : ");
        String email = input.nextLine();
        
        // TODO: try to encapsulate the # check for all classes in the parent classes
        if (email.equals("#")) {
            input.close();
            return FrameName.GUEST_VIEW;
        }
        
        System.out.println("password : ");
        String password = input.nextLine();
        input.close();
        Response result = controller.logIn(email, password);
        System.out.println(result.value);
        
        if (result.success) {
           if (controller.isAdmin())
            return FrameName.HOME_ADMIN;
           else 
            return FrameName.HOME_USER;
        } else {
            System.out.println(result.value);
            return FrameName.SIGN_IN;
        }
    }

}
