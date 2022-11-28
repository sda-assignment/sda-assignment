package payments.entities;

import datastore.Entity;

public class Service implements Entity {
    public final String name;

    public Service(String name) {
        this.name = name;
    }

    public String storify() {
        return name;
    }
}
