package payments.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import common.Util;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import handlers.Handler;
import handlers.HandlerFactory;
import handlers.HandlerResponse;
import payments.common.Response;
import payments.common.enums.TransactionType;
import payments.controllers.paymentstrategies.PayCashOnDelivery;
import payments.controllers.paymentstrategies.PayWithCreditCard;
import payments.controllers.paymentstrategies.PayWithWallet;
import payments.controllers.paymentstrategies.PaymentStrategy;
import payments.entities.Provider;
import payments.entities.ServiceProviderTransaction;
import payments.entities.Transaction;
import payments.entities.User;

public class PaymentController {
    private Relation<Provider> providerRelation;
    private Relation<Transaction> transactionRelation;
    private Relation<ServiceProviderTransaction> spTransactionRelation;
    private Relation<User> userRelation;
    private LogInSession logInSession;

    public PaymentController(Relation<Provider> providerRelation, Relation<Transaction> transactionRelation,
            Relation<ServiceProviderTransaction> spTransactionRelation, Relation<User> userRelation,
            LogInSession logInSession) {
        this.providerRelation = providerRelation;
        this.transactionRelation = transactionRelation;
        this.spTransactionRelation = spTransactionRelation;
        this.userRelation = userRelation;
        this.logInSession = logInSession;
    }

    private Response payToProvider(String serviceName, String providerName, HashMap<String, String> request,
            PaymentStrategy paymentStrategy) throws EntitySaveException {
        ArrayList<Provider> providers = providerRelation
                .select(p -> p.serviceName.equals(serviceName) && p.name.equals(providerName));
        if (providers.size() == 0)
            return new Response(false, "An error has occurred");
        Provider provider = providers.get(0);
        HandlerFactory handlerFactory = new HandlerFactory();
        Handler handler = handlerFactory.getHandler(provider.handlerName);
        HandlerResponse handlerRes = handler.validateAndHandleRequest(request);
        if (!handlerRes.success)
            return new Response(false,
                    "An error has occurred please contact an administrator:\n" + handlerRes.errorMessage);
        double amountToDeduct = handlerRes.amount;
        // TODO: account for discounts
        Response paymentRes = paymentStrategy.pay(amountToDeduct);
        if (!paymentRes.success)
            return paymentRes;
        Transaction transactionToInsert = new Transaction(
                Util.incrementOrInitialize(transactionRelation.selectMax(t -> t.id)),
                logInSession.getLoggedInUser().email,
                LocalDateTime.now(), amountToDeduct, TransactionType.PAYMENT);
        transactionRelation
                .insert(transactionToInsert);
        spTransactionRelation.insert(
                new ServiceProviderTransaction(transactionToInsert.id, provider.serviceName, provider.name));
        return paymentRes;
    }

    public Response payUsingWallet(String serviceName, String providerName, HashMap<String, String> request)
            throws EntitySaveException {
        PaymentStrategy payWithWalletStrategy = new PayWithWallet(userRelation, logInSession.getLoggedInUser());
        return payToProvider(serviceName, providerName, request, payWithWalletStrategy);
    }

    public Response payUsingCreditCard(String serviceName, String providerName, HashMap<String, String> request,
            String cardNumber) throws EntitySaveException {
        PaymentStrategy payWithCreditCardStrategy = new PayWithCreditCard(cardNumber);
        return payToProvider(serviceName, providerName, request, payWithCreditCardStrategy);
    }

    public Response payCashOnDelivery(String serviceName, String providerName, HashMap<String, String> request)
            throws EntitySaveException {
        PaymentStrategy cashOnDeliveryStrategy = new PayCashOnDelivery();
        return payToProvider(serviceName, providerName, request, cashOnDeliveryStrategy);
    }
}
