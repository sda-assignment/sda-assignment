package handlers;

import java.util.HashMap;

import common.HandlerName;
import common.HandlerResponse;

public class QuarterlyLandlineHandler extends Handler {
    private static double QUARTERLY_PRICE = 1000;

    public HandlerName getHandlerName() {
        return HandlerName.QUARTERLY_LANDLINE;
    }

    public String[] getRequestKeys() {
        return new String[] { "landline" };
    }

    public String getConstraints() {
        return "The landline must be 8 digits";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String landline = request.get("landline");
        if (landline == null || landline.length() != 8)
            return new HandlerResponse("Invalid landline number");
        return new HandlerResponse(QUARTERLY_PRICE);
    }
}
