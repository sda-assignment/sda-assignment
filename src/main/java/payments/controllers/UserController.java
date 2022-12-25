package payments.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import payments.common.enums.TransactionType;
import payments.controllers.auth.Context;
import payments.controllers.auth.Authenticator;
import payments.controllers.request.RechargeWalletBody;
import payments.controllers.response.UserResponse;
import payments.entities.Transaction;
import payments.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class UserController {
    private Model<User> userModel;
    private Authenticator authenticator;
    private Model<Transaction> transactionModel;

    public UserController(Model<User> userModel, Authenticator authenticator, Model<Transaction> transactionModel) {
        this.userModel = userModel;
        this.authenticator = authenticator;
        this.transactionModel = transactionModel;
    }

    @PostMapping("/recharge")
    public void rechargeWallet(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody RechargeWalletBody body) {
        if (!Util.isPositiveInt(body.cardNumber))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card number");
        if (body.amount < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount");

        Context ctx = authenticator.getContextFromAuthHeader(authHeader);
        userModel.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + body.amount),
                u -> u.email.equals(ctx.email));
        transactionModel.insert(new Transaction(Util.incrementOrInitialize(transactionModel.selectMax(t -> t.id)),
                ctx.email, LocalDateTime.now(), -body.amount, TransactionType.ADD_TO_WALLET,
                "None", "None"));
    }

    @GetMapping(value = "/profile")
    @ResponseBody
    public UserResponse profile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Context ctx = authenticator.getContextFromAuthHeader(authHeader);
        User user = userModel.select(u -> u.email.equals(ctx.email)).get(0);
        return new UserResponse(user.email, user.username, user.isAdmin, user.wallet);
    }

}
