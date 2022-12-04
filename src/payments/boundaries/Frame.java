package payments.boundaries;

import datastore.exceptions.EntityException;

public abstract class Frame {

    public abstract FrameName getFrameName();

    protected abstract FrameName display() throws EntityException;

    public FrameName displayWithInstruction() throws EntityException
    {
        System.out.println("press # in the first entry field to go back to home ");
        return display();
    }

}
