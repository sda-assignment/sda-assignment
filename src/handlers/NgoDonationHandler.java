package handlers;

import java.util.HashMap;

import common.HandlerName;
import common.HandlerResponse;
import common.Util;

public class NgoDonationHandler extends Handler {
    public HandlerName getHandlerName() {
        return HandlerName.NGO_DONATION;
    }

    public String[] getRequestKeys() {
        return new String[] { "amount" };
    }

    public String getConstraints() {
        return "";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String amount = request.get("amount");
        if (!Util.isPositiveFloat(amount))
            return new HandlerResponse("Invalid amount");
        return new HandlerResponse(Double.parseDouble(amount));
    }
}
