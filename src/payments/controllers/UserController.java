package payments.controllers;

import java.time.LocalDateTime;

import common.Util;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.common.Response;
import payments.common.enums.TransactionType;
import payments.entities.Transaction;
import payments.entities.User;

public class UserController {
    private Relation<User> userRelation;
    private LogInSession logInSession;
    private Relation<Transaction> transactionRelation;

    public UserController(Relation<User> userRelation, LogInSession logInSession,
            Relation<Transaction> transactionRelation) {
        this.userRelation = userRelation;
        this.logInSession = logInSession;
        this.transactionRelation = transactionRelation;
    }

    public Response rechargeWallet(double amount, String cardNumber) throws EntitySaveException {
        if (!Util.isPositiveInt(cardNumber))
            return new Response(false, "Invalid card number");
        if (amount < 0)
            return new Response(false, "Invalid amount");
        userRelation.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + amount),
                u -> u.email.equals(logInSession.getLoggedInUser().email));
        transactionRelation.insert(new Transaction(Util.incrementOrInitialize(transactionRelation.selectMax(t -> t.id)),
                logInSession.getLoggedInUser().email, LocalDateTime.now(), -amount, TransactionType.ADD_TO_WALLET,
                "None", "None"));
        return new Response(true, "Wallet recharged successfully");
    }
}
