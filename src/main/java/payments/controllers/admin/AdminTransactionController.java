package payments.controllers.admin;

import java.util.ArrayList;

import payments.entities.Transaction;
import datastore.Model;

public class AdminTransactionController {
    private Model<Transaction> model;

    public AdminTransactionController(Model<Transaction> model) {
        this.model = model;
    }

    public ArrayList<Transaction> getAllTransactions() {
        return model.select(t -> true);
    }
}
