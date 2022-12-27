package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import datastore.Model;
import payments.controllers.auth.Authenticator;
import payments.controllers.auth.Context;
import payments.entities.RefundRequest;

@RestController
public class RefundController {
    private Model<RefundRequest> refundRequestModel;
    private Authenticator authenticator;

    public RefundController(Model<RefundRequest> refundRequestModel, Authenticator authenticator) {
        this.refundRequestModel = refundRequestModel;
        this.authenticator = authenticator;
    }

    @GetMapping("/refunds")
    @ResponseBody
    public ArrayList<RefundRequest> listRefundsForUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Context ctx = authenticator.getContextOrFail(authHeader);
        return refundRequestModel.select(r -> r.userEmail.equals(ctx.email));
    }
}
