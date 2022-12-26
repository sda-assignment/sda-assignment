package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import payments.common.enums.RefundRequestStatus;
import payments.common.enums.TransactionType;
import payments.controllers.auth.Context;
import payments.controllers.auth.Authenticator;
import payments.controllers.request.RefundRequestBody;
import payments.entities.RefundRequest;
import payments.entities.Transaction;

@RestController
public class RefundController {
    private Model<RefundRequest> refundRequestModel;
    private Model<Transaction> transactionModel;
    private Authenticator authenticator;

    public RefundController(Model<RefundRequest> refundRequestModel, Model<Transaction> transactionModel,
            Authenticator authenticator) {
        this.refundRequestModel = refundRequestModel;
        this.transactionModel = transactionModel;
        this.authenticator = authenticator;
    }

    @PostMapping("/refund")
    public void requestRefund(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody RefundRequestBody body) {
        Context ctx = authenticator.getContextOrFail(authHeader);

        if (refundRequestModel.recordExists(r -> r.transactionId == body.transactionId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You already have a refund request on this transaction");

        ArrayList<Transaction> targetTransactions = transactionModel.select(t -> t.id == body.transactionId);
        if (targetTransactions.size() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction not found");
        Transaction targetTransaction = targetTransactions.get(0);
        if (targetTransaction.type == TransactionType.REFUND)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't request a refund to a refund transaction");

        refundRequestModel.insert(new RefundRequest(
                Util.incrementOrInitialize(refundRequestModel.selectMax(r -> r.id)),
                body.transactionId, RefundRequestStatus.PENDING, ctx.email));
    }
}
