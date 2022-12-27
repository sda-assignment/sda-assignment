package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import payments.controllers.auth.Context;
import payments.common.enums.RefundRequestStatus;
import payments.common.enums.TransactionType;
import payments.controllers.auth.Authenticator;
import payments.entities.RefundRequest;
import payments.entities.Transaction;

@RestController
public class TransactionController {
    private Model<Transaction> transactionModel;
    private Model<RefundRequest> refundRequestModel;
    private Authenticator authenticator;

    public TransactionController(Model<Transaction> transactionModel, Model<RefundRequest> refundRequestModel,
            Authenticator authenticator) {
        this.transactionModel = transactionModel;
        this.refundRequestModel = refundRequestModel;
        this.authenticator = authenticator;
    }

    @GetMapping("/transactions")
    @ResponseBody
    public ArrayList<Transaction> getTransactionsForUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Context ctx = authenticator.getContextOrFail(authHeader);
        return transactionModel.select(t -> t.userEmail.equals(ctx.email));
    }

    @GetMapping("/transactions/{id}")
    @ResponseBody
    public Transaction getTransactionForUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("id") int id) {
        Context ctx = authenticator.getContextOrFail(authHeader);
        Transaction transaction = transactionModel.selectOne(t -> t.userEmail.equals(ctx.email) && t.id == id);
        if (transaction == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a transaction with this id");
        return transaction;
    }

    @PostMapping("/transactions/{id}/refund")
    public void requestRefund(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable int id) {
        Context ctx = authenticator.getContextOrFail(authHeader);

        if (refundRequestModel.recordExists(r -> r.userEmail.equals(ctx.email) && r.transactionId == id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You already have a refund request on this transaction");

        Transaction targetTransaction = transactionModel.selectOne(t -> t.userEmail.equals(ctx.email) && t.id == id);
        if (targetTransaction == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        if (targetTransaction.type == TransactionType.REFUND)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't request a refund to a refund transaction");

        refundRequestModel.insert(new RefundRequest(
                Util.incrementOrInitialize(refundRequestModel.selectMax(r -> r.id)),
                id, RefundRequestStatus.PENDING, ctx.email));
    }
}
