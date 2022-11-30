package payments;

import datastore.Relation;
import datastore.exceptions.EntityLoadException;
import datastore.exceptions.EntitySaveException;
import payments.entities.User;
import payments.entities.builders.UserBuilder;

public class App {
    public static void main(String[] args) throws EntityLoadException, EntitySaveException {
        System.out.println("hello world");

        Relation<User> userRelation = new Relation<User>("users", new UserBuilder());
        // userRelation.insert(new User("ali", "asd", "asd", false, 12.2));
        // userRelation.insert(new User("alix", "asd", "asd", false, 22.2));
        // userRelation.insert(new User("alid", "asd", "asd", false, 15.2));
        double amount = userRelation.selectMax(u -> u.wallet);
        System.out.println(amount);
        System.out.println(userRelation.select(u -> true));
        userRelation.update(u -> new User(u.email, "aloka", u.password, u.isAdmin, u.wallet),
                u -> u.username.equals("ali"));
    }
}
