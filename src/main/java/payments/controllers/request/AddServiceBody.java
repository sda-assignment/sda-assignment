package payments.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AddServiceBody {
    public final String name;

    @JsonCreator
    public AddServiceBody(String name) {
        this.name = name;
    }
}
