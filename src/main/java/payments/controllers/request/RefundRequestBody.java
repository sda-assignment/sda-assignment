package payments.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefundRequestBody {
    public final int transactionId;

    @JsonCreator
    public RefundRequestBody(@JsonProperty("transactionId") int transactionId) {
        this.transactionId = transactionId;
    }
}
