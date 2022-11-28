package payments.entities.builders;

import datastore.EntityBuilder;
import payments.entities.RefundRequest;
import payments.entities.enums.RefundRequestStatus;

public class RefundRequestBuilder implements EntityBuilder<RefundRequest> {
    public RefundRequest fromString(String refundRequest) {
        String[] splitted = refundRequest.split(":");
        return new RefundRequest(Integer.parseInt(splitted[0]), Double.parseDouble(splitted[1]), splitted[2],
                RefundRequestStatus.valueOf(splitted[3]));
    }
}
