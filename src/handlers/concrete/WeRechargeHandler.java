package handlers.concrete;

import java.util.HashMap;

import common.Util;
import handlers.AbstractHandler;
import handlers.HandlerName;
import handlers.HandlerResponse;

public class WeRechargeHandler extends AbstractHandler {
    public HandlerName getHandlerName() {
        return HandlerName.WE_RECHARGE;
    }

    public String[] getRequestKeys() {
        return new String[] { "amount", "phone" };
    }

    public String getConstraints() {
        return "Every phone number must begin with 015 and have 11 digits (inclusive)";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String phone = request.get("phone");
        String amount = request.get("amount");
        if (phone == null || !phone.startsWith("015") || phone.length() != 11)
            return new HandlerResponse("Invalid phone number");
        if (!Util.isPositiveFloat(amount))
            return new HandlerResponse("Invalid amount");
        return new HandlerResponse(Double.parseDouble(amount));
    }
}
