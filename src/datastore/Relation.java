package datastore;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import datastore.exceptions.EntityLoadException;
import datastore.exceptions.EntitySaveException;

public class Relation<T extends Entity> {
    private final String DATA_PATH = "data/";
    private String path;
    ArrayList<T> records;
    DataStoreObjectBuilder<T> recordBuilder;

    public Relation(String fileName, DataStoreObjectBuilder<T> recordBuilder) {
        this.path = DATA_PATH + fileName;
        this.recordBuilder = recordBuilder;
        this.records = new ArrayList<T>();
    }

    public void save() throws EntitySaveException {
        String recordsStr = "";
        for (T record : records) {
            recordsStr = recordsStr.concat(record.storify()) + "\n";
        }
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(recordsStr);
            fileWriter.close();
        } catch (Exception e) {
            throw new EntitySaveException("Failed to write to file: " + path + "\n" + e.getStackTrace());
        }
    }

    public void load() throws EntityLoadException {
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
            throw new EntityLoadException("Failed to read file: " + path + "\n" + e.getStackTrace());
        }
    }

    public void insert(T record) throws EntitySaveException {
        this.records.add(record);
        save();
    }

    public ArrayList<T> select(SelectionFunction<T> selection) {
        ArrayList<T> result = new ArrayList<T>();
        for (T record : records) {
            if (selection.function(record)) {
                result.add(record);
            }
        }
        return result;
    }

    public void update(T newRecord, SelectionFunction<T> selection) throws EntitySaveException {
        for (int i = 0; i < records.size(); i++) {
            if (selection.function(records.get(i))) {
                records.set(i, newRecord);
            }
        }
        save();
    }
}
