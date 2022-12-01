package payments.controllers;

import java.util.ArrayList;

import payments.entities.Transaction;
import payments.entities.User;
import payments.entities.enums.RefundRequestStatus;
import payments.entities.Discount;
import payments.entities.RefundRequest;
import payments.entities.Service;
import payments.common.LogInSession;
import payments.common.Response;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;

public class UserTransactionController {
    private Relation<Transaction> transaction;
    private Relation<User> user;
    private Relation<RefundRequest> refundRequest;
    private Relation<Service> service;
    private LogInSession session;
    private UserDiscountController discountController;

    public UserTransactionController(Relation<Transaction> relation, Relation<User> relation2,
            Relation<RefundRequest> relation3, Relation<Service> relation4, LogInSession S,
            UserDiscountController discountController) {
        this.transaction = relation;
        this.user = relation2;
        this.refundRequest = relation3;
        this.service = relation4;
        this.session = S;
        this.discountController = discountController;
    }

    // utility function
    private Discount getUserDiscount(String serviceName) throws EntitySaveException {
        User current = session.getLoggedInUser();
        Discount maxDiscount = discountController.getMaxDiscount(current.email, serviceName);
        if (maxDiscount.type != null) {
            discountController.addUsedDiscount(maxDiscount);
        }
        return maxDiscount;
    }

    // utility function
    private Response walletPaymentValidation(double amount, String serviceName) {
        User current = session.getLoggedInUser();

        if (current.wallet < amount) {
            return new Response(false, "Failed to perform payment: Not enough balance in wallet \n");
        }

        return new Response(true, "Payment details are valid \n");
    }

    // utility function
    private Response creditCardPaymentValidation(double amount, String serviceName, String creditCard) {
        if (creditCard.length() != 12) {
            return new Response(false,
                    "Failed to perform payment: please enter a valid credit card number (must be 12 digits) \n");
        }

        return new Response(true, "Payment details are valid \n");
    }

    public Response payViaWallet(double amount, String serviceName) throws EntitySaveException {
        User current = session.getLoggedInUser();
        Response result = walletPaymentValidation(amount, serviceName);

        if (result.success == true) {
            Discount maxDiscount = getUserDiscount(serviceName);
            final double amountRemoved = amount - (amount * maxDiscount.percentage);
            user.update(u -> new User(u.email, u.username, u.password, false, (u.wallet - amountRemoved)),
                    u -> u.email.equals(current.email));

            return new Response(true, "Service " + serviceName + " was for Payed with discount of "
                    + maxDiscount.percentage + " % Via Wallet, current remaining wallet balance : " + current.wallet
                    + " \n");
        } else {
            return result;
        }

    }

    public Response payViaCreditCard(double amount, String serviceName, String creditCard) throws EntitySaveException {
        User current = session.getLoggedInUser();
        Response result = creditCardPaymentValidation(amount, serviceName, creditCard);
        if (result.success == true) {
            Discount maxDiscount = getUserDiscount(serviceName);
            final double amountRemoved = amount - (amount * maxDiscount.percentage);
            user.update(u -> new User(u.email, u.username, u.password, false, (u.wallet - amountRemoved)),
                    u -> u.email.equals(current.email));

            return new Response(true, "Service " + serviceName + " was for Payed with discount of "
                    + maxDiscount.percentage + " % Via Credit Card.   \n");
        } else {
            return result;
        }

    }

    public Response refundRequest(double amount, String ServiceName) throws EntitySaveException {
        User current = session.getLoggedInUser();
        int id = refundRequest.selectMax(r -> r.id);

        refundRequest
                .insert(new RefundRequest(id + 1, amount, ServiceName, RefundRequestStatus.PENDING, current.email));
        return new Response(true, "Refund Request Sent to admins \n");
    }

    public Response chargeWallet(int amount, String creditCard) throws EntitySaveException {
        User current = session.getLoggedInUser();
        if (creditCard.length() != 12) {
            return new Response(false,
                    "Failed to charge wallet : please enter a valid credit card number (12 digits minimum) \n");
        }

        user.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + amount),
                u -> u.email == current.email);
        return new Response(true, "wallet charged by " + amount + "$ \n");
    }

    public ArrayList<Service> searchService(String serviceName) {
        return service.select(s -> s.name.equals(serviceName));

    }

    public ArrayList<Transaction> getTransactionHistory() {
        User current = session.getLoggedInUser();
        return transaction.select(t -> t.email.equals(current.email));

    }
}
