package payments;

import java.util.ArrayList;

import datastore.Relation;
import datastore.exceptions.EntityException;
import datastore.exceptions.EntityLoadException;
import datastore.exceptions.EntitySaveException;
import handlers.Handler;
import handlers.HandlerFactory;
import handlers.HandlerName;
import payments.entities.Discount;
import payments.entities.FormElement;
import payments.entities.FormElementChoice;
import payments.entities.RefundRequest;
import payments.entities.Service;
import payments.entities.Transaction;
import payments.entities.UsedDiscount;
import payments.entities.User;
import payments.entities.Provider;
import payments.entities.builders.DiscountBuilder;
import payments.entities.builders.FormElementBuilder;
import payments.entities.builders.FormElementChoiceBuilder;
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
import payments.boundaries.concretes.user.BecomeAdmin;
import payments.boundaries.concretes.user.DiscountList;
import payments.boundaries.concretes.user.HomeUser;
import payments.boundaries.concretes.user.ListAllProviders;
import payments.boundaries.concretes.user.PayForService;
import payments.boundaries.concretes.user.UserRefundRequest;
import payments.common.enums.FormElementType;
import payments.controllers.AuthController;
import payments.controllers.DiscountController;
import payments.controllers.FormElementController;
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
import payments.controllers.admin.AdminUserController;

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
        Relation<FormElementChoice> formElementChoiceRelation = new Relation<FormElementChoice>("formElementChoice",
                new FormElementChoiceBuilder());
        AuthController authController = new AuthController(userRelation, session);
        DiscountController discountController = new DiscountController(discountRelation, usedDiscount, session);
        UserController userController = new UserController(userRelation, session);
        ProviderController providerController = new ProviderController(providerRelation, formElementRelation);
        PaymentController paymentController = new PaymentController(providerRelation, transactionRelation,
                userRelation, discountController, authController);
        RefundController refundController = new RefundController(refundRequestRelation, transactionRelation,
                session);
        TransactionController transactionController = new TransactionController(transactionRelation, session);
        ServiceController serviceController = new ServiceController(serviceRelation);
        AdminDiscountController adminDiscountController = new AdminDiscountController(discountRelation);
        AdminRefundController adminRefundController = new AdminRefundController(refundRequestRelation,
                userRelation, transactionRelation);
        AdminTransactionController adminTransactionController = new AdminTransactionController(
                transactionRelation);
        AdminUserController adminUserController = new AdminUserController(userRelation);
        FormElementController formElementController = new FormElementController(formElementChoiceRelation);
        Frame currentFrame = new GuestView();
        Frame[] frameArr = new Frame[] { currentFrame, new SignIn(authController), new SignUp(authController),
                new HomeUser(session, authController), new DiscountList(discountController),
                new AddToWallet(userController), new ListAllProviders(providerController),
                new PayForService(paymentController, providerController, formElementController),
                new UserRefundRequest(refundController, transactionController),
                new HomeAdmin(authController),
                new AdminAddDiscount(adminDiscountController, serviceController),
                new AdminRefund(adminRefundController),
                new ListAllTransactions(adminTransactionController),
                new BecomeAdmin(adminUserController, authController) };
        Router router = new Router(frameArr, currentFrame);

        serviceRelation.removeAll();
        providerRelation.removeAll();
        formElementRelation.removeAll();
        formElementChoiceRelation.removeAll();
        for (String serviceName : new String[] { "Mobile Recharge Service", "Internet Payment Service",
                "Landline Service", "Donation" }) {
            serviceRelation.insert(new Service(serviceName));
        }
        providerRelation.insert(new Provider("Mobile Recharge Service", "Orange", false, HandlerName.ORANGE_RECHARGE));
        providerRelation.insert(new Provider("Mobile Recharge Service", "We", false, HandlerName.WE_RECHARGE));
        providerRelation
                .insert(new Provider("Mobile Recharge Service", "Etisalat", false, HandlerName.ETISALAT_RECHARGE));
        providerRelation
                .insert(new Provider("Mobile Recharge Service", "Vodafone", false, HandlerName.VODAFONE_RECHARGE));

        providerRelation.insert(new Provider("Internet Payment Service", "Orange", false, HandlerName.ORANGE_INTERNET));
        providerRelation.insert(new Provider("Internet Payment Service", "We", false, HandlerName.WE_INTERNET));
        providerRelation
                .insert(new Provider("Internet Payment Service", "Etisalat", false, HandlerName.ETISALAT_INTERNET));
        providerRelation
                .insert(new Provider("Internet Payment Service", "Vodafone", false, HandlerName.VODAFONE_INTERNET));

        providerRelation.insert(new Provider("Landline Service", "Monthly", false, HandlerName.MONTHLY_LANDLINE));
        providerRelation.insert(new Provider("Landline Service", "Quarterly", false, HandlerName.QUARTERLY_LANDLINE));

        providerRelation
                .insert(new Provider("Donation", "Cancer Hospital", true, HandlerName.CANCER_HOSPITAL_DONATION));
        providerRelation.insert(new Provider("Donation", "Schools", true, HandlerName.SCHOOL_DONATION));
        providerRelation.insert(new Provider("Donation", "NGO", true, HandlerName.NGO_DONATION));

        HandlerFactory handlerFactory = new HandlerFactory();
        ArrayList<Provider> allProviders = providerRelation.select(p -> true);
        for (Provider provider : allProviders) {
            Handler handler = handlerFactory.getHandler(provider.handlerName);
            String[] requestKeys = handler.getRequestKeys();
            for (String key : requestKeys) {
                formElementRelation.insert(new FormElement(key, provider.serviceName, provider.name,
                        FormElementType.TEXT_FIELD, key + " " + "(" + handler.getConstraints() + ")"));
            }
        }
        router.mainLoop();
    }
}
