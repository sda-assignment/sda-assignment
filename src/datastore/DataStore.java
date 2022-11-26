package datastore;

import java.util.ArrayList;

public class DataStore {
    private ArrayList<RecordList> recordLists;

    public DataStore() {
        recordLists = new ArrayList<RecordList>();
    }

    public DataStore addRecordList(RecordList recordList) {
        recordLists.add(recordList);
        return this;
    }
}
