package payments;

import java.time.LocalDateTime;

import datastore.Relation;
import datastore.exceptions.EntityLoadException;
import datastore.exceptions.EntitySaveException;
import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.entities.User;
import payments.entities.builders.RefundRequestBuilder;
import payments.entities.builders.TransactionBuilder;
import payments.entities.builders.UserBuilder;
import payments.entities.enums.RefundRequestStatus;
import payments.entities.enums.TransactionType;
import payments.boundaries.AdminRefundView;

public class App {
    public static void main(String[] args) throws EntityLoadException, EntitySaveException {
        System.out.println("hello world");

        Relation<User> userRelation = new Relation<User>("users", new UserBuilder());
        userRelation.insert(new User("ali", "asd", "asd", false, 12.2));
        userRelation.insert(new User("alix", "asd", "asd", false, 22.2));
        userRelation.insert(new User("alid", "asd", "asd", false, 15.2));
        double amount = userRelation.selectMax(u -> u.wallet);
        System.out.println(amount);
        System.out.println(userRelation.select(u -> true));
        userRelation.update(u -> new User(u.email, "aloka", u.password, u.isAdmin, u.wallet),
                u -> u.username.equals("ali"));


        Relation<RefundRequest> refundRelation = new Relation<RefundRequest>("refundRequests" , new RefundRequestBuilder());
        refundRelation.insert(new RefundRequest(0, 0, 2 , RefundRequestStatus.PENDING, "ahmedwanas@gmail.com"));
        refundRelation.insert(new RefundRequest(1, 110, 1 , RefundRequestStatus.PENDING, "ahmedwaelwanas@gmail.com"));
        refundRelation.insert(new RefundRequest(2, 2132130, 0 , RefundRequestStatus.PENDING, "waelwanas@gmail.com"));

        Relation<Transaction> transactionRelation = new Relation<Transaction>("transactions", new TransactionBuilder());
        transactionRelation.insert(new Transaction(0, "waelwanas@gmail.com", LocalDateTime.now(), 0, TransactionType.REFUND));
        transactionRelation.insert(new Transaction(1, "ahmedwaelwanas@gmail.com", LocalDateTime.now(), 110, TransactionType.REFUND));
        transactionRelation.insert(new Transaction(2, "ahmedwanas@gmail.com", LocalDateTime.now(), 2132130, TransactionType.REFUND));

        AdminRefundView adminView = new AdminRefundView(refundRelation, userRelation, transactionRelation);

        adminView.displayRequests();
    }
}
