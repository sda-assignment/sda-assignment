package payments;

import common.Util;
import datastore.Relation;
import datastore.exceptions.EntityException;
import datastore.exceptions.EntityLoadException;
import datastore.exceptions.EntitySaveException;
import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.entities.User;
import payments.entities.builders.RefundRequestBuilder;
import payments.entities.builders.TransactionBuilder;
import payments.entities.builders.UserBuilder;
import payments.boundaries.AdminRefundView;
import payments.boundaries.Router;
import payments.controllers.AdminRefundController;

public class App {
    public static void main(String[] args) throws EntityException, EntitySaveException, EntityLoadException {
        System.out.println("isPositiveFloat");
        System.out.println(Util.isPositiveFloat("123"));
        System.out.println(Util.isPositiveFloat("-123"));
        System.out.println(Util.isPositiveFloat("123.2"));
        System.out.println(Util.isPositiveFloat("+"));
        System.out.println("isPositiveInt");
        System.out.println(Util.isPositiveInt("123"));
        System.out.println(Util.isPositiveInt("-123"));
        System.out.println(Util.isPositiveInt("123.2"));
        System.out.println(Util.isPositiveInt("+"));

        Relation<User> userRelation = new Relation<User>("users", new UserBuilder());
        // userRelation.insert(new User("ali", "asd", "asd", false, 12.2));
        // userRelation.insert(new User("alix", "asd", "asd", false, 22.2));
        // userRelation.insert(new User("alid", "asd", "asd", false, 15.2));
        // double amount = userRelation.selectMax(u -> u.wallet);
        // System.out.println(amount);
        System.out.println(userRelation.select(u -> true));
        userRelation.update(u -> new User(u.email, "aloka", u.password, u.isAdmin, u.wallet),
                u -> u.username.equals("ali"));

        Relation<Transaction> transactionRelation = new Relation<Transaction>("transactions", new TransactionBuilder());
        // transactionRelation
        // .insert(new Transaction(0, "waelwanas@gmail.com", LocalDateTime.now(), 0,
        // TransactionType.REFUND));
        // transactionRelation.insert(
        // new Transaction(1, "ahmedwaelwanas@gmail.com", LocalDateTime.now(), 110,
        // TransactionType.REFUND));
        // transactionRelation.insert(
        // new Transaction(2, "ahmedwanas@gmail.com", LocalDateTime.now(), 2132130,
        // TransactionType.REFUND));

        Relation<RefundRequest> refundRelation = new Relation<RefundRequest>("refundRequests",
                new RefundRequestBuilder());
        // refundRelation.insert(new RefundRequest(0, 0, RefundRequestStatus.PENDING,
        // "ahmedwanas@gmail.com"));
        // refundRelation.insert(new RefundRequest(1, 1, RefundRequestStatus.PENDING,
        // "ahmedwaelwanas@gmail.com"));
        // refundRelation.insert(new RefundRequest(2, 2, RefundRequestStatus.PENDING,
        // "waelwanas@gmail.com"));
        AdminRefundController adminRefundController = new AdminRefundController(refundRelation, userRelation,
                transactionRelation);
        AdminRefundView adminView = new AdminRefundView(adminRefundController);
        Router r = new Router(adminView);
        r.execute();

    }
}
