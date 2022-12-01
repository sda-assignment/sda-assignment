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
    private ArrayList<T> records;
    private EntityBuilder<T> recordBuilder;

    public Relation(String fileName, EntityBuilder<T> recordBuilder) throws EntityLoadException {
        this.path = DATA_PATH + fileName;
        this.recordBuilder = recordBuilder;
        this.records = new ArrayList<T>();
        load();
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
            throw new EntitySaveException("Failed to write to file: " + path + "\n" + e.toString());
        }
    }

    private void load() throws EntityLoadException {
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
            throw new EntityLoadException("Failed to read file: " + path + "\n" + e.toString());
        }
    }

    public void insert(T record) throws EntitySaveException {
        this.records.add(record);
        save();
    }

    public <B extends Comparable<B>> B selectMax(RecordProject<T, B> RecordProject) {
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

    public ArrayList<T> select(RecordFilter<T> filter) {
        ArrayList<T> result = new ArrayList<T>();
        for (T record : records) {
            if (filter.apply(record)) {
                result.add(record);
            }
        }
        return result;
    }

    public boolean entityExists(RecordFilter<T> filter) {
        ArrayList<T> result = select(filter);
        return result.size() > 0;
    }

    public void update(RecordUpdate<T> updater, RecordFilter<T> filter) throws EntitySaveException {
        for (int i = 0; i < records.size(); i++) {
            if (filter.apply(records.get(i))) {
                records.set(i, updater.apply(records.get(i)));
            }
        }
        save();
    }
}
