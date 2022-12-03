package datastore;

public interface RecordFilterStrategy<T> {
    boolean apply(T obj);
}
