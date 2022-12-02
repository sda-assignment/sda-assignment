package handlers;

import java.util.HashMap;

import common.HandlerName;
import common.HandlerResponse;
import common.Util;

public class OrangeRechargeHandler extends Handler {
    public HandlerName getHandlerName() {
        return HandlerName.ORANGE_RECHARGE;
    }

    public String[] getRequestKeys() {
        return new String[] { "amount", "phone" };
    }

    public String getConstraints() {
        return "Every phone number must begin with 012 and have 11 digits (inclusive)";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String phone = request.get("phone");
        String amount = request.get("amount");
        if (phone == null || !phone.startsWith("012") || phone.length() != 11)
            return new HandlerResponse("Invalid phone number");
        if (!Util.isPositiveFloat(amount))
            return new HandlerResponse("Invalid amount");
        return new HandlerResponse(Double.parseDouble(amount));
    }
}
