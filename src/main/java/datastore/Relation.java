package datastore;

import java.util.ArrayList;

public class Relation<T> {
    private ArrayList<T> records;

    public Relation() {
        this.records = new ArrayList<T>();
    }

    public void insert(T record) {
        this.records.add(record);
    }

    public <B extends Comparable<B>> B selectMax(RecordProjectStrategy<T, B> RecordProject) {
        if (records.size() == 0) {
            return null;
        }
        B max = RecordProject.apply(records.get(0));
        for (int i = 1; i < records.size(); ++i) {
            B afterProjection = RecordProject.apply(records.get(i));
            if (afterProjection.compareTo(max) > 0) {
                max = afterProjection;
            }
        }
        return max;
    }

    public ArrayList<T> select(RecordFilterStrategy<T> filter) {
        ArrayList<T> result = new ArrayList<T>();
        for (T record : records) {
            if (filter.apply(record)) {
                result.add(record);
            }
        }
        return result;
    }

    public boolean recordExists(RecordFilterStrategy<T> filter) {
        ArrayList<T> result = select(filter);
        return result.size() > 0;
    }

    public ArrayList<T> update(RecordUpdateStrategy<T> updater, RecordFilterStrategy<T> filter) {
        ArrayList<T> result = new ArrayList<T>();
        for (int i = 0; i < records.size(); i++) {
            if (filter.apply(records.get(i))) {
                records.set(i, updater.apply(records.get(i)));
                result.add(records.get(i));
            }
        }
        return result;
    }
}
