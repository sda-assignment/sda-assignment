package payments.entities;

import java.time.LocalDateTime;

import datastore.Entity;
import payments.entities.enums.TransactionType;

public class Transaction implements Entity {
    public final String email;
    public final LocalDateTime timestamp;
    public final double amount;
    public final TransactionType type;

    public Transaction(String email, LocalDateTime timestamp, double amount, TransactionType type) {
        this.email = email;
        this.timestamp = timestamp;
        this.amount = amount;
        this.type = type;
    }

    public String storify() {
        return email + ":" + timestamp.toString() + ":" + amount + ":" + type;
    }
}
