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
        System.out.println(userRelation.select(u -> true));
        userRelation.update(u -> new User(u.email, "aloka", u.password, u.isAdmin, u.wallet),
                u -> u.username.equals("ali"));
    }
}
