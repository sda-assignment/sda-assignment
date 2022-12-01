package payments.entities;

import datastore.Entity;
import payments.entities.enums.RefundRequestStatus;

public class RefundRequest implements Entity {
    public final int id;
    public final double amount;
    public final int transactionId;
    public final RefundRequestStatus status;
    public final String userEmail;

    public RefundRequest(int id, double amount, int transactionId, RefundRequestStatus status, String userEmail) {
        this.id = id;
        this.amount = amount;
        this.transactionId = transactionId;
        this.status = status;
        this.userEmail = userEmail;
    }

    public String storify() {
        return id + ":" + amount + ":" + transactionId + ":" + status + ":" + userEmail;
    }
}
