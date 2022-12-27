package payments.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import datastore.Model;
import payments.controllers.auth.Context;
import payments.entities.RefundRequest;

@RestController
public class RefundController {
    private Model<RefundRequest> refundRequestModel;

    public RefundController(Model<RefundRequest> refundRequestModel) {
        this.refundRequestModel = refundRequestModel;
    }

    @GetMapping("/refunds")
    @ResponseBody
    public ArrayList<RefundRequest> listRefunds(@RequestAttribute("context") Context ctx) {
        return refundRequestModel.select(r -> r.userEmail.equals(ctx.email));
    }
}
