package payments.controllers;

import java.util.ArrayList;

import payments.entities.Transaction;
import datastore.Relation;

public class AdminTransactionController {
    private Relation<Transaction> relation;

    public AdminTransactionController(Relation<Transaction> relation) {
        this.relation = relation;
    }

    public ArrayList<Transaction> getAllTransactions() {
        return relation.select(t -> true);
    }
}
