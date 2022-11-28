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
    ArrayList<Entity> records;
    EntityBuilder<T> recordBuilder;

    public Relation(String fileName, EntityBuilder<T> recordBuilder) {
        this.path = DATA_PATH + fileName;
        this.recordBuilder = recordBuilder;
        this.records = new ArrayList<Entity>();
    }

    public void save() throws EntitySaveException {
        String recordsStr = "";
        for (Entity record : records) {
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

    public ArrayList<Entity> select(RecordFilter selection) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity record : records) {
            if (selection.apply(record)) {
                result.add(record);
            }
        }
        return result;
    }

    public void update(T newRecord, RecordFilter selection) throws EntitySaveException {
        for (int i = 0; i < records.size(); i++) {
            if (selection.apply(records.get(i))) {
                records.set(i, newRecord);
            }
        }
        save();
    }
}
