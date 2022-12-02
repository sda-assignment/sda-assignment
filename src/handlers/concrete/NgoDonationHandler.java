package handlers.concrete;

import java.util.HashMap;

import common.Util;
import handlers.AbstractHandler;
import handlers.HandlerName;
import handlers.HandlerResponse;

public class NgoDonationHandler extends AbstractHandler {
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
