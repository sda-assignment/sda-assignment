package datastore;

public interface RecordUpdate<T> {
    T apply(T obj);
}
