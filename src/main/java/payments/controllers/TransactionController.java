// package payments.controllers;

// import java.util.ArrayList;

// import datastore.Model;
// import payments.entities.Transaction;

// public class TransactionController {
//     private Model<Transaction> transactionModel;
//     private AuthController authController;

//     public TransactionController(Model<Transaction> transactionModel, AuthController authController) {
//         this.transactionModel = transactionModel;
//         this.authController = authController;
//     }

//     public ArrayList<Transaction> getTransactionsForUser() {
//         return transactionModel.select(t -> t.userEmail.equals(authController.getLoggedInUser().email));
//     }
// }
