package payments.boundaries.concretes.user;

import payments.entities.User;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.common.Response;
import payments.controllers.AuthController;
import payments.controllers.LogInSession;
import java.util.Scanner;

public class HomeUser extends Frame {
    private User currentUser;
    private AuthController authcontroller;

    public HomeUser(LogInSession S, AuthController authController) {
        currentUser = S.getLoggedInUser();
        this.authcontroller = authController;
        
    }
    
    public FrameName getFrameName ()
    {
        return FrameName.HOME_USER;
    }

    protected FrameName display()
    {
        System.out.format("%15s","HOME : User \n");
        System.out.println("Wallet: " + currentUser.wallet);
        System.out.println("1. List all and search services\n2. Pay for a service\n3.Request a refund\n4. Add to wallet\n5. Check Discounts \n6. Sign Out \n");
        System.out.println("Choice: ");

        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        input.close();
        
        if(option.equals("#") || option.equals("6"))
        {
            Response obj = authcontroller.logOut();
            System.out.println(obj.value);
            return FrameName.GUEST_VIEW;

        }
        else if (option.equals("1"))
        {
            return FrameName.LIST_ALL_PROVIDERS;
        }
        else if (option.equals("2"))
        {
            return FrameName.SEARCH_SERVICE_PROVIDER;

        }
        else if (option.equals("3"))
        {
            return FrameName.REFUND_REQUEST;

        }
        else if (option.equals("4"))
        {
            return FrameName.ADD_AMOUNT_TO_WALLET;

        }
        else if (option.equals("5"))
        {
            return FrameName.DISCOUNT;

        }

        System.out.println("Please enter a valid input ");
        return FrameName.HOME_USER;
    }

    
}
