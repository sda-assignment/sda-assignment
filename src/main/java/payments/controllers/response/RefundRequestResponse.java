package payments.controllers.response;

import java.time.LocalDateTime;

import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.enums.RefundRequestStatus;
import payments.enums.TransactionType;

public class RefundRequestResponse {
    public final int id;
    public final RefundRequestStatus status;
    public final String serviceName;
    public final String providerName;
    public final double amount;
    public final String userEmail;
    public final LocalDateTime timeStamp;
    public TransactionType type;

    public RefundRequestResponse(RefundRequest refundRequest, Transaction transaction) {
        this.id = refundRequest.id;
        this.status = refundRequest.status;
        this.serviceName = transaction.serviceName;
        this.providerName = transaction.providerName;
        this.amount = transaction.amount;
        this.userEmail = transaction.userEmail;
        this.timeStamp = transaction.timestamp;
        this.type = transaction.type;
    }
}
