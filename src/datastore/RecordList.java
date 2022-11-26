package datastore;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import datastore.exceptions.RecordListInitializationException;
import datastore.exceptions.RecordListLoadException;
import datastore.exceptions.RecordListSaveException;

public class RecordList<T extends DataStoreObject, B extends DataStoreObjectBuilder> {
    private String fileName;
    ArrayList<T> records;
    B recordBuilder;

    public RecordList(String fileName, B recordBuilder) throws RecordListInitializationException {
        this.fileName = fileName;
        this.recordBuilder = recordBuilder;
        this.records = new ArrayList<T>();
    }

    public void save() throws RecordListSaveException {
        String recordsStr = "";
        for (T record: records) {
            recordsStr = recordsStr.concat(record.toString()) + "\n";
        }
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(recordsStr);
            fileWriter.close();
        } catch (IOException e) {
            throw new RecordListSaveException("Failed to write to file: " + fileName);
        }
    }

    public void load() throws RecordListLoadException {
    }
}
