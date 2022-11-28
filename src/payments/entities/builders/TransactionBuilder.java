package payments.entities.builders;

import java.time.LocalDateTime;

import datastore.EntityBuilder;
import payments.entities.Transaction;
import payments.entities.enums.TransactionType;

public class TransactionBuilder implements EntityBuilder<Transaction> {
    public Transaction fromString(String transaction) {
        String[] splitted = transaction.split(":");
        return new Transaction(splitted[0], LocalDateTime.parse(splitted[1]), Integer.parseInt(splitted[2]),
                TransactionType.valueOf(splitted[3]));
    }
}
