package payments.entities.builders;

import java.time.LocalDateTime;

import datastore.EntityBuilder;
import payments.entities.Transaction;
import payments.entities.enums.TransactionType;

public class TransactionBuilder implements EntityBuilder<Transaction> {
    public Transaction fromString(String transaction) {
        String[] splitted = transaction.split(":");
        int i = 0;
        return new Transaction(Integer.parseInt(splitted[i++]), splitted[i++], LocalDateTime.parse(splitted[i++]),
                Integer.parseInt(splitted[i++]),
                TransactionType.valueOf(splitted[i++]));
    }
}
