package payments.boundaries;

import payments.common.Response;
import datastore.exceptions.EntityException;

public abstract class Frame {

    public abstract void display();

    public abstract Response input();

    public abstract void execute() throws EntityException;

}
