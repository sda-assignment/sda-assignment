package payments.boundaries;

import datastore.exceptions.EntityException;
import payments.boundaries.EnumViews.FrameName;

public abstract class Frame {

    protected String frameName;

    public void setFrameName(String Name) {
        frameName = Name;
    }

    public String getFrameName() {
        return frameName;
    }

    public abstract FrameName display() throws EntityException;

}
