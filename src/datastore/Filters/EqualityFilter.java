package datastore.Filters;

import datastore.*; 

// public class EqualityFilter <T extends Comparable<? super T>> implements RecordFilter<T> {
//     private T target;

//     // needs change if filter is not a complete record
//     public EqualityFilter (T condition)
//     {
//         target = condition;
//     }
//     public boolean apply(T obj)
//     {
//         if (target.compareTo(obj) == 0)
//             return true;
//         else
//             return false;

//     }
// }

public class EqualityFilter implements RecordFilter {
    private Entity target;

    // needs change if filter is not a complete record
    public EqualityFilter (Entity condition)
    {
        target = condition;
    }
    public boolean apply(Entity obj)
    {
        if (target.compareTo(obj) == 0)
            return true;
        else
            return false;

    }
}