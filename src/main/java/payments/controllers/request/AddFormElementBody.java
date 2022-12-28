package payments.controllers.request;

import payments.enums.FormElementType;

public class AddFormElementBody {
    public final String name;
    public final String info;
    public final FormElementType type;

    public AddFormElementBody(String name, String info, FormElementType type) {
        this.name = name;
        this.info = info;
        this.type = type;
    }
}
