package payments.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddDiscountBody {
    public final double percentage;

    @JsonCreator
    public AddDiscountBody(@JsonProperty("percentage") double percentage) {
        this.percentage = percentage;
    }
}
