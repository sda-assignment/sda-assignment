package payments.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.Util;
import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.entities.User;
import payments.entities.enums.RefundRequestStatus;
import payments.entities.enums.TransactionType;

public class AdminRefundController {
    private Relation<RefundRequest> refundRelation;
    private Relation<User> userRelation;
    private Relation<Transaction> transactionRelation;

    public AdminRefundController(Relation<RefundRequest> refundRelation, Relation<User> userRelation,
            Relation<Transaction> transactionRelation) {
        this.userRelation = userRelation;
        this.refundRelation = refundRelation;
        this.transactionRelation = transactionRelation;
    }

    public ArrayList<RefundRequest> getRefundRequests() {
        return refundRelation.select(r -> true);
    }

    public Response<String> acceptRefund(int rid) throws EntitySaveException {
        ArrayList<RefundRequest> refunds = refundRelation.update(
                r -> new RefundRequest(r.id, r.transactionId, RefundRequestStatus.ACCEPTED, r.userEmail),
                r -> r.id == rid);
        if (refunds.size() == 0)
            return new Response<String>(false, "An error has occurred");

        RefundRequest targetRefund = refunds.get(0);
        ArrayList<Transaction> transactions = transactionRelation.select(t -> t.id == targetRefund.transactionId);
        if (transactions.size() == 0)
            return new Response<String>(false, "An error has occurred");
        Transaction targetTransaction = transactions.get(0);
        ArrayList<User> users = userRelation.update(
                u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + targetTransaction.amount),
                u -> u.email.equals(targetRefund.userEmail));
        if (users.size() > 0) {
            User updatedUser = users.get(0);
            transactionRelation
                    .insert(new Transaction(Util.incrementOrInitialize(transactionRelation.selectMax(t -> t.id)),
                            updatedUser.email, LocalDateTime.now(), targetTransaction.amount,
                            TransactionType.REFUND));
        }
        return new Response<String>(true, "Refund accepted");
    }

    public Response<String> rejectRefund(int rid) throws EntitySaveException {
        refundRelation.update(
                r -> new RefundRequest(r.id, r.transactionId, RefundRequestStatus.REJECTED, r.userEmail),
                r -> r.id == rid);
        return new Response<String>(true, "Refund rejected");
    }
}
