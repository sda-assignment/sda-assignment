package handlers.concrete;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import handlers.Handler;
import handlers.HandlerName;
import handlers.HandlerResponse;

public class VodafoneInternetHandler extends Handler {
    private static final Set<String> allowedBandwidth = new HashSet<String>(Arrays.asList("150", "300", "700"));

    public HandlerName getHandlerName() {
        return HandlerName.VODAFONE_INTERNET;
    }

    public String[] getRequestKeys() {
        return new String[] { "bandwidth", "landline" };
    }

    public String getConstraints() {
        return "The landline must be 8 digits\nThe bandwidth must be one of the following: 150, 300, 700";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String landline = request.get("landline");
        String bandwidth = request.get("bandwidth");
        if (landline == null || landline.length() != 8)
            return new HandlerResponse("Invalid landline number");
        if (!allowedBandwidth.contains(bandwidth))
            return new HandlerResponse("Invalid bandwidth");

        return new HandlerResponse(Double.parseDouble(bandwidth) * 0.9);
    }
}
