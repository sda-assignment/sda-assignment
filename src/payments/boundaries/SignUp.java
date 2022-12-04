package payments.boundaries;

import java.util.Scanner;

import datastore.exceptions.EntityException;
import payments.boundaries.EnumViews.FrameName;
import payments.common.Response;
import payments.controllers.AuthController;

public class SignUp extends Frame {

    private AuthController controller;

    public SignUp(AuthController controller) {
        this.controller = controller;
        frameName = "signUp";
    }

    @Override
    public FrameName display() throws EntityException {
        
        Scanner input = new Scanner(System.in);
        System.out.println("Sign up \n");
        System.out.println("email : ");
        String email = input.nextLine();
        
        if (email.equals("#")) 
        {
            input.close();
            return FrameName.guestView;
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
            return FrameName.homeUser;
        } 
        else 
        {
            return FrameName.signUp;
        }
    }

}
