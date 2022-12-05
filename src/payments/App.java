package payments;

import datastore.Relation;
import datastore.exceptions.EntityException;
import datastore.exceptions.EntityLoadException;
import datastore.exceptions.EntitySaveException;

import payments.entities.Discount;
import payments.entities.FormElement;
import payments.entities.RefundRequest;
import payments.entities.Service;
import payments.entities.Transaction;
import payments.entities.UsedDiscount;
import payments.entities.User;
import payments.entities.Provider;
import payments.entities.builders.DiscountBuilder;
import payments.entities.builders.FormElementBuilder;
import payments.entities.builders.ProviderBuilder;
import payments.entities.builders.RefundRequestBuilder;
import payments.entities.builders.ServiceBuilder;
import payments.entities.builders.TransactionBuilder;
import payments.entities.builders.UsedDiscountBuilder;
import payments.entities.builders.UserBuilder;
import payments.boundaries.Frame;
import payments.boundaries.Router;
import payments.boundaries.concretes.admin.AdminAddDiscount;
import payments.boundaries.concretes.admin.AdminRefund;
import payments.boundaries.concretes.admin.HomeAdmin;
import payments.boundaries.concretes.admin.ListAllTransactions;
import payments.boundaries.concretes.common.GuestView;
import payments.boundaries.concretes.common.SignIn;
import payments.boundaries.concretes.common.SignUp;
import payments.boundaries.concretes.user.AddToWallet;
import payments.boundaries.concretes.user.DiscountList;
import payments.boundaries.concretes.user.HomeUser;
import payments.boundaries.concretes.user.ListAllProviders;
import payments.boundaries.concretes.user.PayForService;
import payments.boundaries.concretes.user.UserRefundRequest;
import payments.controllers.AuthController;
import payments.controllers.DiscountController;
import payments.controllers.LogInSession;
import payments.controllers.PaymentController;
import payments.controllers.ProviderController;
import payments.controllers.RefundController;
import payments.controllers.ServiceController;
import payments.controllers.TransactionController;
import payments.controllers.UserController;
import payments.controllers.admin.AdminDiscountController;
import payments.controllers.admin.AdminRefundController;
import payments.controllers.admin.AdminTransactionController;

public class App {
    public static void main(String[] args) throws EntityException, EntitySaveException, EntityLoadException {
        Relation<User> userRelation = new Relation<User>("users", new UserBuilder());
        LogInSession session = new LogInSession();
        Relation<UsedDiscount> usedDiscount = new Relation<UsedDiscount>("usedDiscounts",
                new UsedDiscountBuilder());
        Relation<Transaction> transactionRelation = new Relation<Transaction>("transactions",
                new TransactionBuilder());
        Relation<Service> serviceRelation = new Relation<Service>("services", new ServiceBuilder());
        Relation<RefundRequest> refundRequestRelation = new Relation<RefundRequest>("refundRequests",
                new RefundRequestBuilder());
        Relation<Provider> providerRelation = new Relation<Provider>("providers", new ProviderBuilder());
        Relation<FormElement> formElementRelation = new Relation<FormElement>("formElements",
                new FormElementBuilder());
        Relation<Discount> discountRelation = new Relation<Discount>("discounts", new DiscountBuilder());
        AuthController authController = new AuthController(userRelation, session);
        DiscountController discountController = new DiscountController(discountRelation, usedDiscount, session);
        UserController userController = new UserController(userRelation, session);
        ProviderController providerController = new ProviderController(providerRelation, formElementRelation);
        PaymentController paymentController = new PaymentController(providerRelation, transactionRelation,
                userRelation, discountController, session);
        RefundController refundController = new RefundController(refundRequestRelation, transactionRelation,
                session);
        TransactionController transactionController = new TransactionController(transactionRelation, session);
        ServiceController serviceController = new ServiceController(serviceRelation);
        AdminDiscountController adminDiscountController = new AdminDiscountController(discountRelation);
        AdminRefundController adminRefundController = new AdminRefundController(refundRequestRelation,
                userRelation, transactionRelation);
        AdminTransactionController adminTransactionController = new AdminTransactionController(
                transactionRelation);
        Frame currentFrame = new GuestView();
        Frame[] frameArr = new Frame[] { currentFrame, new SignIn(authController), new SignUp(authController),
                new HomeUser(session, authController), new DiscountList(discountController),
                new AddToWallet(userController), new ListAllProviders(providerController),
                new PayForService(paymentController, providerController),
                new UserRefundRequest(refundController, transactionController),
                new HomeAdmin(authController),
                new AdminAddDiscount(adminDiscountController, serviceController),
                new AdminRefund(adminRefundController),
                new ListAllTransactions(adminTransactionController) };
        Router router = new Router(frameArr, currentFrame);
        router.mainLoop();
    }
}
