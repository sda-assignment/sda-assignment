package datastore;

public interface RecordUpdateStrategy<T> {
    T apply(T obj);
}
