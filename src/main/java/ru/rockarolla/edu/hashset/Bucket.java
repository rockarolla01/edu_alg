package ru.rockarolla.edu.hashset;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by davydov on 05-Sep-16.
 */
class Bucket<T> implements Iterable<T> {

    private BucketEntry<T> headEntry;
    private BucketEntry<T> tailEntry;

    private int size = 0;

    @Override
    public Iterator<T> iterator() {
        return new BucketEntryIterator();
    }

    public void add(T object) {
        final BucketEntry<T> entry = new BucketEntry<>(object);
        if (headEntry == null) {
            headEntry = entry;
            tailEntry = headEntry;
        } else {
            tailEntry.setNext(entry);
            tailEntry = entry;
        }
        increaseSize();
    }

    public int size() {
        return size;
    }

    private void decreaseSize() {
        size--;
    }

    private void increaseSize() {
        size++;
    }

    private class BucketEntryIterator implements Iterator<T> {

        private BucketEntry<T> next = headEntry;
        private BucketEntry<T> prev = null;
        private BucketEntry<T> prePrev = null;
        private boolean lastElementRemoved = false;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if (! hasNext()) {
                throw new NoSuchElementException();
            }
            final T object = next.getValue();

            final BucketEntry<T> current = next;
            if (!lastElementRemoved) {
                prePrev = prev;
            }
            prev = current;
            next = current.getNext();

            lastElementRemoved = false;
            return object;
        }

        @Override
        public void remove() {
            if (lastElementRemoved) {
                throw new IllegalStateException("The element at current position has already been removed");
            }
            if (prev == null) {
                throw new IllegalStateException("The iterator has not been used yet, there's no element to remove");
            }

            // first of all, let's knock into a shape the head and the tail elements of the bucket
            if (prePrev == null) { // this means, we're removing the read element
                headEntry = next;
            }
            if (next == null || next.getNext() == null) {
                tailEntry = next;
            }
            // now, let's tackle the entries structure
            if (prePrev != null) {
                prePrev.setNext(next);
            }
            decreaseSize();

            // ... and the iterator's bowels as well
            lastElementRemoved = true;
            prev = null; // actually, this is not necessary, but let's make this element inaccessible, just in case
        }
    }

    private static class BucketEntry<T> {
        private T value;
        private BucketEntry nextEntry;

        public BucketEntry(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        private BucketEntry<T> getNext() {
            return nextEntry;
        }

        public void setNext(BucketEntry nextEntry) {
            this.nextEntry = nextEntry;
        }
    }
}
