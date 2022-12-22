package payments.entities;

public class Service {
    public final String name;

    public Service(String name) {
        this.name = name;
    }

    public String storify() {
        return name;
    }
}
