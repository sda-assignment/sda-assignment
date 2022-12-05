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

    public FrameName getFrameName ()
    {
        return FrameName.SIGN_UP;
    }

    @Override
    protected FrameName display() throws EntityException {
        
        Scanner input = new Scanner(System.in);
        System.out.format("%15s","Sign up \n");
        System.out.println("email : ");
        String email = input.nextLine();
        
        if (email.equals("#")) 
        {
            input.close();
            return FrameName.GUEST_VIEW;
        }
        
        System.out.println("username : ");
        String username = input.nextLine();
        System.out.println("password : ");
        String password = input.nextLine();
        input.close();
        
        Response result = controller.signUp(email,username,password);
        System.out.println(result.value);
        
        if (result.success) 
        {
            return FrameName.HOME_USER;
        } 
        else 
        {
            System.out.println(result.value);
            return FrameName.SIGN_UP;
        }
    }

}
