package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.Response;
import payments.entities.RefundRequest;
import payments.entities.User;
import payments.entities.enums.RefundRequestStatus;

public class AdminRefundController {
    private Relation<RefundRequest> refundRelation;
    private Relation<User> userRelation;

    public AdminRefundController(Relation<RefundRequest> refundRelation, Relation<User> userRelation) {
        this.userRelation = userRelation;
        this.refundRelation = refundRelation;
    }

    public ArrayList<RefundRequest> getRefundRequests() {
        return refundRelation.select(r -> true);
    }

    public Response acceptRefund(int rid) throws EntitySaveException {
        refundRelation.update(
                r -> new RefundRequest(r.id, r.amount, r.transactionId, RefundRequestStatus.ACCEPTED, r.userEmail),
                r -> r.id == rid);

        ArrayList<RefundRequest> targetRefund = refundRelation.select(r -> r.id == rid);
        userRelation.update(
                u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + targetRefund.get(0).amount),
                u -> u.email == targetRefund.get(0).userEmail);
        return new Response(true, "Refund accepted, returning funds to wallet");
    }

    public Response rejectRefund(int rid) throws EntitySaveException {
        refundRelation.update(
                r -> new RefundRequest(r.id, r.amount, r.transactionId, RefundRequestStatus.REJECTED, r.userEmail),
                r -> r.id == rid);
        return new Response(true, "Refund rejected");
    }
}
