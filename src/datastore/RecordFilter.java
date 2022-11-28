package datastore;

public interface RecordFilter {
    boolean apply(Entity obj);
}
