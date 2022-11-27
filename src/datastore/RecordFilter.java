package datastore;

public interface RecordFilter<T> {
    boolean apply(T obj);
}
