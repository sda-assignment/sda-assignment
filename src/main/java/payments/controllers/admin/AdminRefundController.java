package payments.controllers.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import payments.common.enums.RefundRequestStatus;
import payments.common.enums.TransactionType;
import payments.controllers.response.RefundResponse;
import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.entities.User;

@RestController
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

    @GetMapping("/admin/refunds")
    public ArrayList<RefundResponse> listRefundRequests() {
        ArrayList<RefundRequest> refundRequests = refundModel.select(r -> true);
        ArrayList<RefundResponse> rrTransactions = new ArrayList<>();
        for (RefundRequest rr : refundRequests) {
            rrTransactions
                    .add(new RefundResponse(rr, transactionModel.selectOne(t -> t.id == rr.transactionId)));
        }
        return rrTransactions;
    }

    @GetMapping("/admin/refunds/{id}")
    public RefundResponse getRefund(@PathVariable("id") int id) {
        RefundRequest refundRequest = refundModel.selectOne(rr -> rr.id == id);
        if (refundRequest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid refund request id");
        }
        Transaction transaction = transactionModel.selectOne(t -> t.id == refundRequest.transactionId);
        return new RefundResponse(refundRequest, transaction);
    }

    @PostMapping("/admin/refunds/{id}/accept")
    public void acceptRefund(@PathVariable("id") int rid) {
        ArrayList<RefundRequest> refunds = refundModel.update(
                r -> new RefundRequest(r.id, r.transactionId, RefundRequestStatus.ACCEPTED, r.userEmail),
                r -> r.id == rid && r.status == RefundRequestStatus.PENDING);
        if (refunds.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid refund id");

        RefundRequest targetRefund = refunds.get(0);
        Transaction targetTransaction = transactionModel.selectOne(t -> t.id == targetRefund.transactionId);
        if (targetTransaction == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not find the transaction the refund request refers to");

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
    }

    @PostMapping("/admin/refunds/{id}/reject")
    public void rejectRefund(@PathVariable("id") int rid) {
        ArrayList<RefundRequest> updated = refundModel.update(
                r -> new RefundRequest(r.id, r.transactionId, RefundRequestStatus.REJECTED, r.userEmail),
                r -> r.id == rid && r.status == RefundRequestStatus.PENDING);
        if (updated.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid refund request id");
    }
}
