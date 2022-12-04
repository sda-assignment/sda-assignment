package payments.boundaries;

import datastore.exceptions.EntityException;
import java.util.Scanner;

public class GuestView extends Frame {

    @Override
    public void display() {
        System.out.println(frameName + " \n");
        System.out.println("1- Sign In \n 2- Sign Up");
        String userInput = new Scanner(System.in);

    }

    @Override
    public boolean input() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void execute() throws EntityException {
        // TODO Auto-generated method stub

    }

}
