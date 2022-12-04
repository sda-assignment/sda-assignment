package payments.controllers.admin;

import payments.common.Response;
import payments.entities.User;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;

public class AdminUserController {
    private Relation<User> relation;

    public AdminUserController(Relation<User> relation) {
        this.relation = relation;
    }

    public Response setAdmin(String email) throws EntitySaveException {
        relation.update(u -> new User(u.email, u.username, u.password, true, u.wallet), u -> u.email.equals(email));
        return new Response(true, email + " is an admin now");
    }
}
