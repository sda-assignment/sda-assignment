package payments.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import common.Util;
import datastore.Model;
import payments.controllers.auth.Context;
import payments.controllers.request.RechargeWalletBody;
import payments.controllers.response.UserResponse;
import payments.entities.Transaction;
import payments.entities.User;
import payments.enums.TransactionType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class UserController {
    private Model<User> userModel;
    private Model<Transaction> transactionModel;

    public UserController(Model<User> userModel, Model<Transaction> transactionModel) {
        this.userModel = userModel;
        this.transactionModel = transactionModel;
    }

    @PostMapping("/user/recharge")
    public void rechargeWallet(@RequestAttribute("context") Context ctx,
            @RequestBody RechargeWalletBody body) {
        if (!Util.isPositiveInt(body.cardNumber))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card number");
        if (body.amount < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount");

        userModel.update(u -> new User(u.email, u.username, u.password, u.isAdmin, u.wallet + body.amount),
                u -> u.email.equals(ctx.email));
        transactionModel.insert(new Transaction(Util.incrementOrInitialize(transactionModel.selectMax(t -> t.id)),
                ctx.email, LocalDateTime.now(), -body.amount, TransactionType.ADD_TO_WALLET,
                "None", "None"));
    }

    @GetMapping(value = "/user")
    @ResponseBody
    public UserResponse profile(@RequestAttribute("context") Context ctx) {
        User user = userModel.selectOne(u -> u.email.equals(ctx.email));
        return new UserResponse(user);
    }

}
