package payments.entities;

import datastore.Entity;
import payments.Util;
import payments.entities.enums.RefundRequestStatus;

public class RefundRequest implements Entity {
    public final int id;
    public final int transactionId;
    public final RefundRequestStatus status;
    public final String userEmail;

    public RefundRequest(int id, int transactionId, RefundRequestStatus status, String userEmail) {
        this.id = id;
        this.transactionId = transactionId;
        this.status = status;
        this.userEmail = userEmail;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] { id, transactionId, status, userEmail });
    }
}
