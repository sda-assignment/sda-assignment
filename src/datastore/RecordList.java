package datastore;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import datastore.exceptions.RecordListLoadException;
import datastore.exceptions.RecordListSaveException;

public class RecordList {
    private final String DATA_PATH = "data/";
    private String path;
    ArrayList<DataStoreObject> records;
    DataStoreObjectBuilder recordBuilder;

    public RecordList(String fileName, DataStoreObjectBuilder recordBuilder) {
        this.path = DATA_PATH + fileName;
        this.recordBuilder = recordBuilder;
        this.records = new ArrayList<DataStoreObject>();
    }

    public void save() throws RecordListSaveException {
        String recordsStr = "";
        for (DataStoreObject record : records) {
            recordsStr = recordsStr.concat(record.storify()) + "\n";
        }
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(recordsStr);
            fileWriter.close();
        } catch (Exception e) {
            throw new RecordListSaveException("Failed to write to file: " + path + "\n" + e.getStackTrace());
        }
    }

    public void load() throws RecordListLoadException {
        try {
            File file = new File(path);
            file.createNewFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                records.add(recordBuilder.fromString(line));
            }
            scanner.close();
        } catch (Exception e) {
            throw new RecordListLoadException("Failed to read file: " + path + "\n" + e.getStackTrace());
        }
    }

    public void insert(DataStoreObject record) throws RecordListSaveException {
        this.records.add(record);
        save();
    }

    public ArrayList<DataStoreObject> select(SelectionFunction selection) {
        ArrayList<DataStoreObject> result = new ArrayList<DataStoreObject>();
        for (DataStoreObject record : records) {
            if (selection.function(record)) {
                result.add(record);
            }
        }
        return result;
    }

    public void update(DataStoreObject newRecord, SelectionFunction selection) throws RecordListSaveException {
        for (int i = 0; i < records.size(); i++) {
            if (selection.function(records.get(i))) {
                records.set(i, newRecord);
            }
        }
        save();
    }
}
