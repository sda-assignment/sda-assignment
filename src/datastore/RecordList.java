package datastore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import datastore.exceptions.RecordListInitializationException;
import datastore.exceptions.RecordListLoadException;
import datastore.exceptions.RecordListSaveException;

public class RecordList {
    private String fileName;
    ArrayList<DataStoreObject> records;
    DataStoreObjectBuilder recordBuilder;

    public RecordList(String fileName, DataStoreObjectBuilder recordBuilder) throws RecordListInitializationException {
        this.fileName = fileName;
        this.recordBuilder = recordBuilder;
        this.records = new ArrayList<DataStoreObject>();
    }

    public void save() throws RecordListSaveException {
        String recordsStr = "";
        for (DataStoreObject record : records) {
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
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                records.add(recordBuilder.fromString(line));
            }
            scanner.close();
        } catch (IOException e) {
            throw new RecordListLoadException("Failed to read file: " + fileName);
        }
    }
}
