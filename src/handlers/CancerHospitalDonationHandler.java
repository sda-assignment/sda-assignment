package handlers;

import java.util.HashMap;
import java.util.Map;

import common.HandlerName;
import common.HandlerResponse;

public class CancerHospitalDonationHandler extends Handler {
    private static Map<String, Double> TYPES_PRICES = Map.of("beds", 1382.9, "medicine", 500.9);

    public HandlerName getHandlerName() {
        return HandlerName.CANCER_HOSPITAL_DONATION;
    }

    public String[] getRequestKeys() {
        return new String[] { "type" };
    }

    public String getConstraints() {
        return "'type' must be either 'beds' or 'medicine'";
    }

    protected HandlerResponse handleRequestAndGetAmount(HashMap<String, String> request) {
        String type = request.get("type");
        if (type == null || !TYPES_PRICES.containsKey(type))
            return new HandlerResponse("Invalid donation type");
        return new HandlerResponse(TYPES_PRICES.get(type));
    }
}
