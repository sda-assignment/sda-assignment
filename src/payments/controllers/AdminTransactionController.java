package payments.controllers;

import java.util.ArrayList;

import payments.common.LogInSession;
import payments.entities.Transaction;
import datastore.Relation;

public class AdminTransactionController {
    private Relation<Transaction> relation;
    private LogInSession logInSession;

    public AdminTransactionController(Relation<Transaction> relation) {
        this.relation = relation;
    }

    public ArrayList<Transaction> printTransactionHistory() {
        if(logInSession.getLoggedInUser().isAdmin == true)            
                return relation.select(t -> true);
        return null;
    }
}
