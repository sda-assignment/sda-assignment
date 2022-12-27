package payments.entities;

import java.time.LocalDateTime;

import payments.common.enums.TransactionType;

public class Transaction {
    public final int id;
    public final String userEmail;
    public final LocalDateTime timestamp;
    public final double amount;
    public final TransactionType type;
    public final String serviceName;
    public final String providerName;

    public Transaction(int id, String email, LocalDateTime timestamp, double amount, TransactionType type,
            String serviceName, String providerName) {
        this.id = id;
        this.userEmail = email;
        this.timestamp = timestamp;
        this.amount = amount;
        this.type = type;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }
}
