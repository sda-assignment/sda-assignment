package handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import common.HandlerName;
import common.HandlerResponse;

public class EtisalatInternetHandler extends Handler {
    private static final Set<String> allowedBandwidth = new HashSet<String>(Arrays.asList("100", "200", "500"));

    public HandlerName getHandlerName() {
        return HandlerName.ETISALAT_INTERNET;
    }

    public String[] getRequestKeys() {
        return new String[] { "bandwidth", "landline" };
    }

    public String getConstraints() {
        return "The landline must be 8 digits\nThe bandwidth must be one of the following: 100, 200, 500";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String landline = request.get("landline");
        String bandwidth = request.get("bandwidth");
        if (landline == null || landline.length() != 8)
            return new HandlerResponse("Invalid landline number");
        if (!allowedBandwidth.contains(bandwidth))
            return new HandlerResponse("Invalid bandwidth");

        return new HandlerResponse(Double.parseDouble(bandwidth) * 0.8);
    }
}
