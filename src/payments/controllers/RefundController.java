package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.ResponseType;
import payments.entities.RefundRequest;
import payments.entities.User;
import payments.entities.enums.RefundRequestStatus;

public class RefundController {

    private Relation<RefundRequest> refundRelation;
    private Relation<User> userRelation;

    public RefundController(Relation<RefundRequest> refunRelation, Relation<User> userRelation) {
        this.userRelation = userRelation;
        this.refundRelation = refunRelation;
    }

    public ArrayList<RefundRequest> getRefundRequests() {
        return refundRelation.select(r -> true);

    }

    public ResponseType evaluateRefund(boolean status, int rid) throws EntitySaveException {
        if (status == true) {
            refundRelation.update(
                    r -> new RefundRequest(r.id, r.amount, r.serviceName, RefundRequestStatus.ACCEPTED, r.userEmail),
                    r -> r.id == rid);

            ArrayList<RefundRequest> current = refundRelation.select(r -> r.id == rid);
            userRelation.update(
                    u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + current.get(0).amount),
                    u -> u.email == current.get(0).userEmail);
            return new ResponseType(true, "Refund got accepted, returning funds to wallet");
        } else {
            refundRelation.update(
                    r -> new RefundRequest(r.id, r.amount, r.serviceName, RefundRequestStatus.REJECTED, r.userEmail),
                    r -> r.id == rid);
            return new ResponseType(false, "Refund got Rejected");
        }
    }

}
