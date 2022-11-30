package payments.controllers;

import payments.common.Response;
import payments.entities.User;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;

public class UserController {
    private Relation<User> relation;

    public UserController(Relation<User> relation) {
        this.relation = relation;
    }

    public Response AddAdmin(String email) throws EntitySaveException {
        relation.update(u -> new User(u.email, u.username, u.password, true, u.wallet), u -> u.email == email);
        return new Response(true, email + "is an admin now");
    }
}
