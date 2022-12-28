package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import payments.controllers.auth.Context;
import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.enums.RefundRequestStatus;
import payments.enums.TransactionType;

@RestController
public class TransactionController {
    private Model<Transaction> transactionModel;
    private Model<RefundRequest> refundRequestModel;

    public TransactionController(Model<Transaction> transactionModel, Model<RefundRequest> refundRequestModel) {
        this.transactionModel = transactionModel;
        this.refundRequestModel = refundRequestModel;
    }

    @GetMapping("/transactions")
    @ResponseBody
    public ArrayList<Transaction> listTransactions(@RequestAttribute("context") Context ctx) {
        return transactionModel.select(t -> t.userEmail.equals(ctx.email));
    }

    @GetMapping("/transactions/{id}")
    @ResponseBody
    public Transaction getTransaction(@RequestAttribute("context") Context ctx,
            @PathVariable("id") int id) {
        Transaction transaction = transactionModel.selectOne(t -> t.userEmail.equals(ctx.email) && t.id == id);
        if (transaction == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a transaction with this id");
        return transaction;
    }

    @PostMapping("/transactions/{id}/refund")
    public void requestRefund(@RequestAttribute("context") Context ctx,
            @PathVariable int id) {

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
