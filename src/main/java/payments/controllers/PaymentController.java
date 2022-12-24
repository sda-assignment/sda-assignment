// package payments.controllers;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.HashMap;

// import common.Util;
// import datastore.Model;
// import handlers.Handler;
// import handlers.HandlerFactory;
// import handlers.HandlerResponse;
// import payments.common.Response;
// import payments.common.enums.TransactionType;
// import payments.controllers.paymentstrategies.PayCashOnDelivery;
// import payments.controllers.paymentstrategies.PayWithCreditCard;
// import payments.controllers.paymentstrategies.PayWithWallet;
// import payments.controllers.paymentstrategies.PaymentStrategy;
// import payments.entities.Discount;
// import payments.entities.Provider;
// import payments.entities.Transaction;
// import payments.entities.User;

// public class PaymentController {
//     private Model<Provider> providerModel;
//     private Model<Transaction> transactionModel;
//     private Model<User> userModel;
//     private DiscountController discountController;
//     private AuthController authController;

//     public PaymentController(Model<Provider> providerModel, Model<Transaction> transactionModel,
//             Model<User> userModel,
//             DiscountController discountController,
//             AuthController authController) {
//         this.providerModel = providerModel;
//         this.transactionModel = transactionModel;
//         this.userModel = userModel;
//         this.discountController = discountController;
//         this.authController = authController;
//     }

//     private Response payToProvider(String serviceName, String providerName, HashMap<String, String> request,
//             PaymentStrategy paymentStrategy) {
//         ArrayList<Provider> providers = providerModel
//                 .select(p -> p.serviceName.equals(serviceName) && p.name.equals(providerName));
//         if (providers.size() == 0)
//             return new Response(false, "An error has occurred");
//         Provider provider = providers.get(0);
//         HandlerFactory handlerFactory = new HandlerFactory();
//         Handler handler = handlerFactory.getHandler(provider.handlerName);
//         HandlerResponse handlerRes = handler.validateAndHandleRequest(request);
//         if (!handlerRes.success)
//             return new Response(false,
//                     "An error has occurred please contact an administrator:\n" + handlerRes.errorMessage);

//         double amountToDeduct = handlerRes.amount;
//         ArrayList<Discount> discounts = discountController.getDiscountsForService(serviceName);
//         for (Discount discount : discounts) {
//             amountToDeduct = amountToDeduct - amountToDeduct * (discount.percentage / 100);
//         }

//         Response paymentRes = paymentStrategy.pay(amountToDeduct);
//         if (!paymentRes.success)
//             return paymentRes;

//         Transaction transactionToInsert = new Transaction(
//                 Util.incrementOrInitialize(transactionModel.selectMax(t -> t.id)),
//                 authController.getLoggedInUser().email,
//                 LocalDateTime.now(), amountToDeduct, TransactionType.PAYMENT, serviceName, providerName);
//         transactionModel
//                 .insert(transactionToInsert);

//         for (Discount discount : discounts) {
//             Response useDiscountRes = discountController.useDiscount(discount.id);
//             if (!useDiscountRes.success)
//                 return useDiscountRes;
//         }

//         return new Response(true, "Paid " + amountToDeduct + "$ to " + providerName + " " + serviceName);
//     }

//     public Response payUsingWallet(String serviceName, String providerName, HashMap<String, String> request)
//             {
//         PaymentStrategy payWithWalletStrategy = new PayWithWallet(userModel, authController.getLoggedInUser());
//         return payToProvider(serviceName, providerName, request, payWithWalletStrategy);
//     }

//     public Response payUsingCreditCard(String serviceName, String providerName, HashMap<String, String> request,
//             String cardNumber) {
//         PaymentStrategy payWithCreditCardStrategy = new PayWithCreditCard(cardNumber);
//         return payToProvider(serviceName, providerName, request, payWithCreditCardStrategy);
//     }

//     public Response payCashOnDelivery(String serviceName, String providerName, HashMap<String, String> request)
//             {
//         PaymentStrategy cashOnDeliveryStrategy = new PayCashOnDelivery();
//         return payToProvider(serviceName, providerName, request, cashOnDeliveryStrategy);
//     }
// }
