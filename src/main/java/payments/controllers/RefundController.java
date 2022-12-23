// package payments.controllers;

// import java.util.ArrayList;

// import common.Util;
// import datastore.Relation;
// import payments.common.Response;
// import payments.common.enums.RefundRequestStatus;
// import payments.common.enums.TransactionType;
// import payments.entities.RefundRequest;
// import payments.entities.Transaction;

// public class RefundController {
//     private Relation<RefundRequest> refundRequestRelation;
//     private Relation<Transaction> transactionRelation;
//     private LogInSession logInSession;

//     public RefundController(Relation<RefundRequest> refundRequestRelation, Relation<Transaction> transactionRelation,
//             LogInSession logInSession) {
//         this.refundRequestRelation = refundRequestRelation;
//         this.transactionRelation = transactionRelation;
//         this.logInSession = logInSession;
//     }

//     public Response requestRefund(int transactionId) {
//         if (refundRequestRelation.recordExists(r -> r.transactionId == transactionId))
//             return new Response(false, "You already have a refund request on this transaction");
//         ArrayList<Transaction> targetTransactions = transactionRelation.select(t -> t.id == transactionId);
//         if (targetTransactions.size() == 0)
//             return new Response(false, "Transaction not found");
//         Transaction targetTransaction = targetTransactions.get(0);
//         if (targetTransaction.type == TransactionType.REFUND)
//             return new Response(false, "Can't request a refund to a refund transaction");

//         refundRequestRelation.insert(new RefundRequest(
//                 Util.incrementOrInitialize(refundRequestRelation.selectMax(r -> r.id)),
//                 transactionId, RefundRequestStatus.PENDING, logInSession.getLoggedInUser().email));
//         return new Response(true, "Refund requested");
//     }
// }
