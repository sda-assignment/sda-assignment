package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import datastore.Model;
import payments.controllers.auth.Context;
import payments.controllers.auth.Authenticator;
import payments.entities.Transaction;

@RestController
public class TransactionController {
    private Model<Transaction> transactionModel;
    private Authenticator authenticator;

    public TransactionController(Model<Transaction> transactionModel, Authenticator authenticator) {
        this.transactionModel = transactionModel;
        this.authenticator = authenticator;
    }

    @GetMapping("/transactions")
    @ResponseBody
    public ArrayList<Transaction> getTransactionsForUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Context ctx = authenticator.getContextFromAuthHeader(authHeader);
        return transactionModel.select(t -> t.userEmail.equals(ctx.email));
    }
}
