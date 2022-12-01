package payments.controllers;

import java.util.ArrayList;
import payments.entities.Transaction;
import payments.entities.User;
import payments.entities.enums.RefundRequestStatus;
import payments.entities.RefundRequest;
import payments.entities.Service;
import payments.common.LogInSession;
import payments.common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;

public class UserTransactionController {
    private Relation<Transaction> transaction;
    private Relation<User> user;
    private Relation<RefundRequest> refundRequest;
    private Relation<Service> service;
    private LogInSession session;

    public UserTransactionController(Relation<Transaction> relation, Relation<User> relation2,
            Relation<RefundRequest> relation3, Relation<Service> relation4, LogInSession S) {
        this.transaction = relation;
        this.user = relation2;
        this.refundRequest = relation3;
        this.service = relation4;
        this.session = S;
    }

    public Response rechargeService(int amount, String serviceName) throws EntitySaveException {

        User current = session.getLoggedInUser();

        if (current.wallet < amount) {
            return new Response(false, "Failed to perform payment: Not enough balance in wallet \n");
        } else {
            ArrayList<Service> array = service.select(s -> s.name.equals(serviceName));
            if (array.size() == 0) {
                return new Response(false, "Failed to perform payment: No such service name exists \n");
            }

            user.update(u -> new User(u.email, u.username, u.password, false, (u.wallet - amount)),
                    u -> u.email.equals(current.email));

            return new Response(true, "Service " + array.get(0).name + " Recharged \n");
        }
    }

    public Response refundRequest(double amount, String ServiceName) throws EntitySaveException {

        User current = session.getLoggedInUser();
        int id = refundRequest.selectMax(r -> r.id);

        refundRequest
                .insert(new RefundRequest(id + 1, amount, ServiceName, RefundRequestStatus.PENDING, current.email));
        return new Response(true, "Refund Request Sent to admins \n");
    }

    public Response chargeWallet(int amount, String creditCard) throws EntitySaveException {
        User current = session.getLoggedInUser();
        if (creditCard.length() != 12) {
            return new Response(false,
                    "Failed to charge wallet : please enter a valid credit card number (12 num. minimum) \n");
        }

        user.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + amount),
                u -> u.email == current.email);
        return new Response(true, "wallet charged by " + amount + "$ \n");
    }

    public ArrayList<Service> searchService(String serviceName) {
        return service.select(s -> s.name.equals(serviceName));

    }

    public ArrayList<Transaction> getTransactionHistory() {

        User current = session.getLoggedInUser();
        return transaction.select(t -> t.email.equals(current.email));

    }
}
