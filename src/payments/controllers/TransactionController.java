package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import payments.entities.Transaction;

public class TransactionController {
    private Relation<Transaction> transactionRelation;
    private LogInSession logInSession;

    public TransactionController(Relation<Transaction> transactionRelation, LogInSession logInSession) {
        this.transactionRelation = transactionRelation;
    }

    public ArrayList<Transaction> getTransactionsForUser() {
        return transactionRelation.select(t -> t.userEmail.equals(logInSession.getLoggedInUser().email));
    }
}
