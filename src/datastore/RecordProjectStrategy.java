package datastore;

public interface RecordProjectStrategy<T, B> {
    B apply(T obj);
}
