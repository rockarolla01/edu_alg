package ru.rockarolla.edu.hashset;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by davydov on 05-Sep-16.
 */
public class SetImpl<T> implements Set<T> {

    private int bucketsCount;
    private final Bucket<T>[] buckets;

    public SetImpl() {
        this(8);
    }

    public SetImpl(int bucketsCount) {
        this.bucketsCount = bucketsCount;
        this.buckets = new Bucket[bucketsCount];
        for (int i = 0; i < bucketsCount; i++) {
            this.buckets[i] = new Bucket<>();
        }
    }

    public boolean add(T object) {
        if (contains(object)) {
            return false;
        }

        final Bucket bucket = getBucketFor(object);
        bucket.add(object);
        return true;
    }

    public boolean remove(T object) {
        final Bucket<T> bucket = getBucketFor(object);
        final Iterator<T> iterator = bucket.iterator();

        while (iterator.hasNext()) {
            final Object item = iterator.next();
            if (equal(item, object)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean contains(T object) {
        for (T obj : getBucketFor(object)) {
            if (equal(obj, object)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        int size = 0;
        for (Bucket bucket : buckets) {
            size += bucket.size();
        }
        return size;
    }

    public Iterator<T> iterator() {
        return new SetIterator();
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MySet{");
        boolean first = true;
        for (T obj : this) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(obj == null ? "null" : obj.toString());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    private Bucket<T> getBucketFor(Object o) {
        final int bucketNum = getBucketNum(o);
        return buckets[bucketNum];
    }

    private int getBucketNum(Object o) {
        return hashCode(o) % bucketsCount;
    }

    private int hashCode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    private boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }


    private class SetIterator implements Iterator<T> {

        private int currBucket = 0;
        private Iterator<T> currIterator = buckets[0].iterator();
        private Iterator<T> prevIterator;
        private boolean currIteratorUsed;

        @Override
        public boolean hasNext() {
            if (!currIterator.hasNext() && currBucket < bucketsCount - 1) {
                currBucket++;
                prevIterator = currIterator;
                currIterator = buckets[currBucket].iterator();
                currIteratorUsed = false;
            }
            return currIterator.hasNext();
        }

        @Override
        public T next() {
            if (!currIterator.hasNext()) {
                throw new NoSuchElementException();
            }

            currIteratorUsed = true;
            return currIterator.next();
        }

        @Override
        public void remove() {
            final Iterator<T> iteratorToRemove = currIteratorUsed ? currIterator : prevIterator;
            if (iteratorToRemove == null) {
                throw new IllegalStateException("The iterator has not been used, cannot remove an element");
            }
            iteratorToRemove.remove();
        }
    }
}
