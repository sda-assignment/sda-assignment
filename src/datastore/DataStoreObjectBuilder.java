package datastore;

public interface DataStoreObjectBuilder<T> {
    T fromString(String string);
}
