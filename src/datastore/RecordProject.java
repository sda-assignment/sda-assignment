package datastore;

public interface RecordProject<T, B> {
    B apply(T obj);
}
