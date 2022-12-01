package payments.entities.builders;

import java.time.LocalDateTime;

import datastore.EntityBuilder;
import payments.entities.Transaction;
import payments.entities.enums.TransactionType;

public class TransactionBuilder implements EntityBuilder<Transaction> {
    public Transaction fromString(String transaction) {
        String[] splitted = transaction.split(":");
        return new Transaction(Integer.parseInt(splitted[0]), splitted[1], LocalDateTime.parse(splitted[2]),
                Integer.parseInt(splitted[3]),
                TransactionType.valueOf(splitted[4]));
    }
}
