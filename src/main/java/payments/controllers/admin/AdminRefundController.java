package payments.controllers.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;

import common.Util;
import datastore.Model;
import payments.common.Response;
import payments.common.enums.RefundRequestStatus;
import payments.common.enums.TransactionType;
import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.entities.User;

public class AdminRefundController {
    private Model<RefundRequest> refundModel;
    private Model<User> userModel;
    private Model<Transaction> transactionModel;

    public AdminRefundController(Model<RefundRequest> refundModel, Model<User> userModel,
            Model<Transaction> transactionModel) {
        this.userModel = userModel;
        this.refundModel = refundModel;
        this.transactionModel = transactionModel;
    }

    public ArrayList<RefundRequest> getRefundRequests() {
        return refundModel.select(r -> true);
    }

    public Response acceptRefund(int rid) {
        ArrayList<RefundRequest> refunds = refundModel.update(
                r -> new RefundRequest(r.id, r.transactionId, RefundRequestStatus.ACCEPTED, r.userEmail),
                r -> r.id == rid);
        if (refunds.size() == 0)
            return new Response(false, "An error has occurred");

        RefundRequest targetRefund = refunds.get(0);
        ArrayList<Transaction> transactions = transactionModel.select(t -> t.id == targetRefund.transactionId);
        if (transactions.size() == 0)
            return new Response(false, "An error has occurred");
        Transaction targetTransaction = transactions.get(0);
        ArrayList<User> users = userModel.update(
                u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + targetTransaction.amount),
                u -> u.email.equals(targetRefund.userEmail));
        if (users.size() > 0) {
            User updatedUser = users.get(0);
            transactionModel
                    .insert(new Transaction(Util.incrementOrInitialize(transactionModel.selectMax(t -> t.id)),
                            updatedUser.email, LocalDateTime.now(), targetTransaction.amount,
                            TransactionType.REFUND, "None", "None"));
        }
        return new Response(true, "Refund accepted");
    }

    public Response rejectRefund(int rid) {
        refundModel.update(
                r -> new RefundRequest(r.id, r.transactionId, RefundRequestStatus.REJECTED, r.userEmail),
                r -> r.id == rid);
        return new Response(true, "Refund rejected");
    }
}
