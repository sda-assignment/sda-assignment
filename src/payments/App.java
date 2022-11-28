package payments;

import datastore.Filters.*;
import payments.entities.User;

public class App {
    public static void main(String[] args) {
        //System.out.println("Hello World!");
        User u1 = new User ( "a", "b","1");
        User u2 = new User ( "b", "c","2");
        EqualityFilter E = new EqualityFilter(u1);
        InEqualityFilter I = new InEqualityFilter(u1);
        GreaterThanFilter G = new GreaterThanFilter(u1);
        LessThanFilter L =  new LessThanFilter (u1);
        if (E.apply(u2))
        {
            System.out.println("U1 is equal to U2");
        }
        else
        {
            System.out.println("U1 is not equal to U2");
        }

        if (I.apply(u2))
        {
            System.out.println("U1 is InEqual to U2");
        }
        else
        {
            System.out.println("U1 is equal to U2");
        }

        if (G.apply(u2))
        {
            System.out.println("U1 is greater to U2");
        }
        else
        {
            System.out.println("U1 is smaller to U2");
        }

        if (L.apply(u2))
        {
            System.out.println("U1 is Less than U2");
        }
        else
        {
            System.out.println("U1 is more than U2");
        }
    }
}
