package handlers;

import java.util.HashMap;

import common.HandlerName;
import common.HandlerResponse;
import common.Util;

public class WeInternetHandler extends Handler {

    public HandlerName getHandlerName() {
        return HandlerName.WE_INTERNET;
    }

    public String[] getRequestKeys() {
        return new String[] { "bandwidth", "landline" };
    }

    public String getConstraints() {
        return "The landline must be 8 digits\nThe bandwidth must be greater than 150 and less than 999";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String landline = request.get("landline");
        String bandwidth = request.get("bandwidth");
        if (landline == null || landline.length() != 8)
            return new HandlerResponse("Invalid landline number");
        if (!Util.isPositiveFloat(bandwidth))
            return new HandlerResponse("Invalid bandwidth");
        double bandwidthDouble = Double.parseDouble(bandwidth);
        if (bandwidthDouble < 150 || bandwidthDouble > 999)
            return new HandlerResponse("Bandwidth out of range");

        return new HandlerResponse(bandwidthDouble * 1.2);
    }
}
