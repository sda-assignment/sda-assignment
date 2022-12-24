package payments.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import handlers.Handler;
import handlers.HandlerFactory;
import handlers.HandlerResponse;
import payments.common.enums.TransactionType;
import payments.controllers.auth.Context;
import payments.controllers.auth.LogInSession;
import payments.controllers.payment_strategies.PayCashOnDelivery;
import payments.controllers.payment_strategies.PayWithCreditCard;
import payments.controllers.payment_strategies.PayWithWallet;
import payments.controllers.payment_strategies.PaymentStrategy;
import payments.controllers.request.CreditCardPaymentBody;
import payments.controllers.request.PaymentBody;
import payments.entities.Discount;
import payments.entities.Provider;
import payments.entities.Transaction;
import payments.entities.User;

@RestController
public class PaymentController {
    private Model<Provider> providerModel;
    private Model<Transaction> transactionModel;
    private Model<User> userModel;
    private DiscountController discountController;
    private LogInSession logInSession;

    public PaymentController(Model<Provider> providerModel, Model<Transaction> transactionModel,
            Model<User> userModel,
            DiscountController discountController,
            LogInSession logInSession) {
        this.providerModel = providerModel;
        this.transactionModel = transactionModel;
        this.userModel = userModel;
        this.discountController = discountController;
        this.logInSession = logInSession;
    }

    private void payToProvider(String email, String serviceName, String providerName, HashMap<String, String> request,
            PaymentStrategy paymentStrategy) {
        ArrayList<Provider> providers = providerModel
                .select(p -> p.serviceName.equals(serviceName) && p.name.equals(providerName));
        if (providers.size() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid service name or provider name");

        Provider provider = providers.get(0);
        HandlerFactory handlerFactory = new HandlerFactory();
        Handler handler = handlerFactory.getHandler(provider.handlerName);
        HandlerResponse handlerRes = handler.validateAndHandleRequest(request);

        if (!handlerRes.success)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The form handler was not able to handle this request:\n" + handlerRes.errorMessage);

        double amountToDeduct = handlerRes.amount;
        ArrayList<Discount> discounts = discountController.getDiscountsForServiceForUser(email, serviceName);
        for (Discount discount : discounts) {
            amountToDeduct = amountToDeduct - amountToDeduct * (discount.percentage / 100);
        }

        paymentStrategy.pay(amountToDeduct);

        Transaction transactionToInsert = new Transaction(
                Util.incrementOrInitialize(transactionModel.selectMax(t -> t.id)),
                email,
                LocalDateTime.now(), amountToDeduct, TransactionType.PAYMENT, serviceName, providerName);
        transactionModel
                .insert(transactionToInsert);

        for (Discount discount : discounts) {
            discountController.useDiscount(email, discount.id);
        }
    }

    @PostMapping("/pay-wallet")
    public void payUsingWallet(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody PaymentBody body) {
        Context ctx = logInSession.getContextFromAuthHeader(authHeader);
        PaymentStrategy payWithWalletStrategy = new PayWithWallet(userModel,
                userModel.select(u -> u.email.equals(ctx.email)).get(0));
        payToProvider(ctx.email, body.serviceName, body.providerName, body.handlerRequest,
                payWithWalletStrategy);
    }

    @PostMapping("/pay-credit-card")
    public void payUsingCreditCard(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody CreditCardPaymentBody body) {
        Context ctx = logInSession.getContextFromAuthHeader(authHeader);
        PaymentStrategy payWithCreditCardStrategy = new PayWithCreditCard(body.cardNumber);
        payToProvider(ctx.email, body.serviceName, body.providerName, body.handlerRequest, payWithCreditCardStrategy);
    }

    @PostMapping("/pay-cash")
    public void payCashOnDelivery(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody PaymentBody body) {
        Context ctx = logInSession.getContextFromAuthHeader(authHeader);
        PaymentStrategy cashOnDeliveryStrategy = new PayCashOnDelivery();
        payToProvider(ctx.email, body.serviceName, body.providerName, body.handlerRequest, cashOnDeliveryStrategy);
    }
}
