package payments.entities;

import java.time.LocalDateTime;

import datastore.Entity;
import payments.Util;
import payments.entities.enums.TransactionType;

public class Transaction implements Entity {
    public final int id;
    public final String userEmail;
    public final LocalDateTime timestamp;
    public final double amount;
    public final TransactionType type;

    public Transaction(int id, String email, LocalDateTime timestamp, double amount, TransactionType type) {
        this.id = id;
        this.userEmail = email;
        this.timestamp = timestamp;
        this.amount = amount;
        this.type = type;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] {id, userEmail, timestamp.toString(), amount, type});
    }
}
