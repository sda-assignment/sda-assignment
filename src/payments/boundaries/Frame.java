package payments.boundaries;

import java.util.Scanner;

import datastore.exceptions.EntityException;

public abstract class Frame {

    public abstract FrameName getFrameName();

    protected abstract FrameName display(Scanner scanner) throws EntityException;

    public FrameName displayWithInstruction(Scanner scanner) throws EntityException {
        System.out.println("press # in the first entry field to go back to home ");
        return display(scanner);
    }

}