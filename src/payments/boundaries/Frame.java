package payments.boundaries;

import payments.common.Response;
import datastore.exceptions.EntityException;

public abstract class Frame {

    protected String frameName;

    // protected Frame next;
    // protected Frame previous;

    public void setFrameName(String Name) {
        frameName = Name;
    }

    public String getFrameName() {
        return frameName;
    }

    public abstract void display();

    public abstract boolean input();

    public abstract void execute() throws EntityException;

    public boolean goHome(String input) {
        return (input.equals("#"));
    }

}
