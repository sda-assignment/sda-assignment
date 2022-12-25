// package payments.controllers;

// import java.util.ArrayList;

// import common.Util;
// import datastore.Model;
// import payments.common.Response;
// import payments.common.enums.RefundRequestStatus;
// import payments.common.enums.TransactionType;
// import payments.entities.RefundRequest;
// import payments.entities.Transaction;

// public class RefundController {
//     private Model<RefundRequest> refundRequestModel;
//     private Model<Transaction> transactionModel;
//     private TokenUtil tokenUtil;

//     public RefundController(Model<RefundRequest> refundRequestModel, Model<Transaction> transactionModel,
//             TokenUtil tokenUtil) {
//         this.refundRequestModel = refundRequestModel;
//         this.transactionModel = transactionModel;
//         this.tokenUtil = tokenUtil;
//     }

//     public Response requestRefund(int transactionId) {
//         if (refundRequestModel.recordExists(r -> r.transactionId == transactionId))
//             return new Response(false, "You already have a refund request on this transaction");
//         ArrayList<Transaction> targetTransactions = transactionModel.select(t -> t.id == transactionId);
//         if (targetTransactions.size() == 0)
//             return new Response(false, "Transaction not found");
//         Transaction targetTransaction = targetTransactions.get(0);
//         if (targetTransaction.type == TransactionType.REFUND)
//             return new Response(false, "Can't request a refund to a refund transaction");

//         refundRequestModel.insert(new RefundRequest(
//                 Util.incrementOrInitialize(refundRequestModel.selectMax(r -> r.id)),
//                 transactionId, RefundRequestStatus.PENDING, tokenUtil.getLoggedInUser().email));
//         return new Response(true, "Refund requested");
//     }
// }
