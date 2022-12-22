package payments.controllers;

import java.util.ArrayList;

import datastore.Relation;
import payments.entities.Transaction;

public class TransactionController {
    private Relation<Transaction> transactionRelation;
    private AuthController authController;

    public TransactionController(Relation<Transaction> transactionRelation, AuthController authController) {
        this.transactionRelation = transactionRelation;
        this.authController = authController;
    }

    public ArrayList<Transaction> getTransactionsForUser() {
        return transactionRelation.select(t -> t.userEmail.equals(authController.getLoggedInUser().email));
    }
}
