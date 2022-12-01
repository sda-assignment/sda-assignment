package payments.controllers;

import common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.Util;
import payments.entities.RefundRequest;
import payments.entities.enums.RefundRequestStatus;

public class UserRefundController {
    private Relation<RefundRequest> refundRequestRelation;
    private LogInSession logInSession;

    public UserRefundController(Relation<RefundRequest> refundRequestRelation, LogInSession logInSession) {
        this.refundRequestRelation = refundRequestRelation;
        this.logInSession = logInSession;
    }

    public Response<String> requestRefund(int transactionId) throws EntitySaveException {
        if (refundRequestRelation.recordExists(r -> r.transactionId == transactionId))
            return new Response<String>(false, "You already have a refund request on this transaction");
        refundRequestRelation.insert(new RefundRequest(
                Util.incrementOrInitialize(refundRequestRelation.selectMax(r -> r.id)),
                transactionId, RefundRequestStatus.PENDING, logInSession.getLoggedInUser().email));
        return new Response<String>(true, "Refund requested");
    }
}
