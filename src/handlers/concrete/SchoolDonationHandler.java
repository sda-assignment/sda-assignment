package handlers.concrete;

import java.util.HashMap;

import common.Util;
import handlers.Handler;
import handlers.HandlerName;
import handlers.HandlerResponse;

public class SchoolDonationHandler extends Handler {
    public HandlerName getHandlerName() {
        return HandlerName.SCHOOL_DONATION;
    }

    public String[] getRequestKeys() {
        return new String[] { "amount" };
    }

    public String getConstraints() {
        return "The amount must be greater than 500 and less than 99999";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String amount = request.get("amount");
        if (!Util.isPositiveFloat(amount))
            return new HandlerResponse("Invalid amount");
        Double amountDouble = Double.parseDouble(amount);
        if (amountDouble < 500 || amountDouble > 99999)
            return new HandlerResponse("Amount out of bound");
        return new HandlerResponse(amountDouble);
    }
}
