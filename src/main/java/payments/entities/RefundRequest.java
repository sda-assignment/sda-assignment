package payments.entities;

import payments.common.enums.RefundRequestStatus;

public class RefundRequest {
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
}
