package datastore;

public abstract class Entity {
    public abstract String storify();

    // add a method to force all methods to compare their attributes
    public abstract int compareTo(Entity conditon);
}
