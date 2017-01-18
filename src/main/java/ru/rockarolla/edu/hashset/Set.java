package ru.rockarolla.edu.hashset;

/**
 * Created by davydov on 05-Sep-16.
 */
public interface Set<T> extends Iterable<T> {
    boolean add(T object);

    boolean remove(T object);

    boolean contains(T object);

    int size();

}
