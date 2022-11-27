package datastore;

public interface EntityBuilder<T> {
    T fromString(String string);
}
