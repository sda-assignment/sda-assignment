package handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import common.HandlerName;
import common.HandlerResponse;

public class OrangeInternetHandler extends Handler {
    private static final Set<String> allowedBandwidth = new HashSet<String>(Arrays.asList("500", "1000"));

    public HandlerName getHandlerName() {
        return HandlerName.ORANGE_INTERNET;
    }

    public String[] getRequestKeys() {
        return new String[] { "bandwidth", "landline" };
    }

    public String getConstraints() {
        return "The landline must be 8 digits\nThe bandwidth must be one of the following: 500, 1000";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String landline = request.get("landline");
        String bandwidth = request.get("bandwidth");
        if (landline == null || landline.length() != 8)
            return new HandlerResponse("Invalid landline number");
        if (!allowedBandwidth.contains(bandwidth))
            return new HandlerResponse("Invalid bandwidth");

        return new HandlerResponse(Double.parseDouble(bandwidth));
    }
}
