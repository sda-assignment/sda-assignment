package payments.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import datastore.Model;
import payments.controllers.auth.Context;
import payments.controllers.response.RefundRequestResponse;
import payments.entities.RefundRequest;
import payments.entities.Transaction;

@RestController
public class RefundController {
    private Model<RefundRequest> refundRequestModel;
    private Model<Transaction> transactionModel;

    public RefundController(Model<RefundRequest> refundRequestModel, Model<Transaction> transactionModel) {
        this.refundRequestModel = refundRequestModel;
        this.transactionModel = transactionModel;
    }

    @GetMapping("/refunds")
    @ResponseBody
    public ArrayList<RefundRequestResponse> listRefunds(@RequestAttribute("context") Context ctx) {
        ArrayList<RefundRequest> refundRequests = refundRequestModel.select(r -> r.userEmail.equals(ctx.email));
        ArrayList<RefundRequestResponse> res = new ArrayList<>();
        for (RefundRequest refundRequest : refundRequests)
            res.add(new RefundRequestResponse(refundRequest,
                    transactionModel.selectOne(t -> t.id == refundRequest.transactionId)));
        return res;
    }
}
