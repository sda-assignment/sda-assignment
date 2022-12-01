package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import payments.common.LogInSession;
import payments.entities.Transaction;

public class UserTransactionController {
    private Relation<Transaction> transactionRelation;
    private LogInSession logInSession;

    public UserTransactionController(Relation<Transaction> transactionRelation, LogInSession logInSession) {
        this.transactionRelation = transactionRelation;
    }

    public ArrayList<Transaction> getTransactionsForUser() {
        return transactionRelation.select(t -> t.userEmail == logInSession.getLoggedInUser().email);
    }
}
