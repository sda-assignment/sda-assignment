// package payments.controllers;

// import java.time.LocalDateTime;

// import common.Util;
// import datastore.Model;
// import payments.common.Response;
// import payments.common.enums.TransactionType;
// import payments.entities.Transaction;
// import payments.entities.User;

// public class UserController {
//     private Model<User> userModel;
//     private TokenUtil tokenUtil;
//     private Model<Transaction> transactionModel;

//     public UserController(Model<User> userModel, TokenUtil tokenUtil,
//             Model<Transaction> transactionModel) {
//         this.userModel = userModel;
//         this.tokenUtil = tokenUtil;
//         this.transactionModel = transactionModel;
//     }

//     public Response rechargeWallet(double amount, String cardNumber) {
//         if (!Util.isPositiveInt(cardNumber))
//             return new Response(false, "Invalid card number");
//         if (amount < 0)
//             return new Response(false, "Invalid amount");
//         userModel.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + amount),
//                 u -> u.email.equals(tokenUtil.getLoggedInUser().email));
//         transactionModel.insert(new Transaction(Util.incrementOrInitialize(transactionModel.selectMax(t -> t.id)),
//                 tokenUtil.getLoggedInUser().email, LocalDateTime.now(), -amount, TransactionType.ADD_TO_WALLET,
//                 "None", "None"));
//         return new Response(true, "Wallet recharged successfully");
//     }
// }
