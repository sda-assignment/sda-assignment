package payments.controllers.response;

import payments.entities.RefundRequest;
import payments.entities.Transaction;

public class RefundRequestTransaction {
    public final RefundRequest refundRequest;
    public final Transaction transaction;

    public RefundRequestTransaction(RefundRequest refundRequest, Transaction transaction) {
        this.refundRequest = refundRequest;
        this.transaction = transaction;
    }
}
