package datastore;

public interface EntityBuilder<T extends Entity> {
    T fromString(String string);
}
