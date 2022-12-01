package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.RefundRequest;
import payments.entities.enums.RefundRequestStatus;

public class RefundRequestBuilder implements EntityBuilder<RefundRequest> {
    public RefundRequest fromString(String refundRequest) {
        String[] splitted = refundRequest.split(":");
        int i = 0;
        return new RefundRequest(Integer.parseInt(splitted[i++]), Double.parseDouble(splitted[i++]),
                Integer.parseInt(splitted[i++]),
                RefundRequestStatus.valueOf(splitted[i++]), splitted[i++]);
    }
}
