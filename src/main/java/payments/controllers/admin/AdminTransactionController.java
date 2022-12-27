package payments.controllers.admin;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import payments.entities.Transaction;
import datastore.Model;

@RestController
public class AdminTransactionController {
    private Model<Transaction> transactionModel;

    public AdminTransactionController(Model<Transaction> model) {
        this.transactionModel = model;
    }

    @GetMapping("/admin/transactions")
    public ArrayList<Transaction> listTransactions() {
        return transactionModel.select(t -> true);
    }
}
