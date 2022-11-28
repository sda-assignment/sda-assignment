package datastore.Filters;
import datastore.*;

public class InEqualityFilter implements RecordFilter {
    private  Entity target;

    public InEqualityFilter (Entity condition)
    {
        target = condition;
    }
    public boolean apply(Entity obj)
    {
        if (target.compareTo(obj) != 0)
            return true;
        else
            return false;

    }
}