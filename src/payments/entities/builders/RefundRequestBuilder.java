package payments.entities.builders;

import datastore.EntityBuilder;
import payments.common.enums.RefundRequestStatus;
import payments.entities.RefundRequest;

public class RefundRequestBuilder implements EntityBuilder<RefundRequest> {
    public RefundRequest fromString(String refundRequest) {
        String[] splitted = refundRequest.split(":");
        int i = 0;
        return new RefundRequest(Integer.parseInt(splitted[i++]),
                Integer.parseInt(splitted[i++]),
                RefundRequestStatus.valueOf(splitted[i++]), splitted[i++]);
    }
}
