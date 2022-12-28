package payments.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import handlers.Handler;
import handlers.HandlerFactory;
import handlers.HandlerResponse;
import payments.common.enums.DiscountType;
import payments.common.enums.TransactionType;
import payments.controllers.auth.Context;
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
    private Model<Transaction> transactionModel;
    private Model<User> userModel;
    private DiscountController discountController;
    private ServiceController serviceController;

    public PaymentController(Model<Transaction> transactionModel,
            Model<User> userModel,
            DiscountController discountController, ServiceController serviceController) {
        this.transactionModel = transactionModel;
        this.userModel = userModel;
        this.discountController = discountController;
        this.serviceController = serviceController;
    }

    private void payToProvider(String email, Provider provider,
            HashMap<String, String> request, PaymentStrategy paymentStrategy) {

        HandlerFactory handlerFactory = new HandlerFactory();
        Handler handler = handlerFactory.getHandler(provider.handlerName);
        HandlerResponse handlerRes = handler.validateAndHandleRequest(request);

        if (!handlerRes.success)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The form handler was not able to handle this request:\n" + handlerRes.errorMessage);

        double amountToDeduct = handlerRes.amount;
        ArrayList<Discount> discounts = discountController.getDiscountsForServiceForUser(email, provider.serviceName);
        for (Discount discount : discounts) {
            amountToDeduct = amountToDeduct - amountToDeduct * (discount.percentage / 100);
        }

        paymentStrategy.pay(amountToDeduct);

        Transaction transactionToInsert = new Transaction(
                Util.incrementOrInitialize(transactionModel.selectMax(t -> t.id)),
                email,
                LocalDateTime.now(), amountToDeduct, TransactionType.PAYMENT, provider.serviceName, provider.name);
        transactionModel
                .insert(transactionToInsert);

        for (Discount discount : discounts) {
            if (discount.type == DiscountType.OVERALL)
                discountController.useDiscount(email, discount.id);
        }
    }

    @PostMapping("/services/{serviceName}/providers/{providerName}/pay-wallet")
    public void payUsingWallet(@RequestAttribute("context") Context ctx,
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName,
            @RequestBody PaymentBody body) {
        Provider provider = serviceController.getServiceProvider(serviceName, providerName);
        PaymentStrategy payWithWalletStrategy = new PayWithWallet(userModel,
                userModel.selectOne(u -> u.email.equals(ctx.email)));
        payToProvider(ctx.email, provider, body.handlerRequest, payWithWalletStrategy);
    }

    @PostMapping("/services/{serviceName}/providers/{providerName}/pay-credit-card")
    public void payUsingCreditCard(@RequestAttribute("context") Context ctx,
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName,
            @RequestBody CreditCardPaymentBody body) {
        Provider provider = serviceController.getServiceProvider(serviceName, providerName);
        PaymentStrategy payWithCreditCardStrategy = new PayWithCreditCard(body.cardNumber);
        payToProvider(ctx.email, provider, body.handlerRequest, payWithCreditCardStrategy);
    }

    @PostMapping("/services/{serviceName}/providers/{providerName}/pay-cash")
    public void payCashOnDelivery(@RequestAttribute("context") Context ctx,
            @PathVariable("serviceName") String serviceName, @PathVariable("providerName") String providerName,
            @RequestBody PaymentBody body) {
        Provider provider = serviceController.getServiceProvider(serviceName, providerName);
        if (!provider.cashOnDelivery)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This provider does not provide cash on delivery");
        PaymentStrategy cashOnDeliveryStrategy = new PayCashOnDelivery();
        payToProvider(ctx.email, provider, body.handlerRequest, cashOnDeliveryStrategy);
    }
}
