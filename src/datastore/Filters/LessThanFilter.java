package datastore.Filters;
import datastore.*;

public class LessThanFilter implements RecordFilter {
    private  Entity target;

    public LessThanFilter (Entity condition)
    {
        target = condition;
    }
    public boolean apply(Entity obj)
    {
        if (target.compareTo(obj) == -1)
            return true;
        else
            return false;

    }
}