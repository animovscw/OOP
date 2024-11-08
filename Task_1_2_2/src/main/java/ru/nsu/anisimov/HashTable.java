package ru.nsu.anisimov;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Hash table implementation supporting operations like put, remove, get, update,
 * containsKey, equality check, iteration, and resizing.
 *
 * @param <K> the type of keys
 * @param <V> the type of mapped values
 */
public class HashTable<K, V> implements Iterable<Map.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final float LOAD_FACTOR = 0.75f;
    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private int modCount;

    /**
     * Constructs an empty hash table with the default initial capacity.
     */
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty hash table with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the hash table
     */
    public HashTable(int initialCapacity) {
        table = new LinkedList[initialCapacity];
        size = 0;
        modCount = 0;
    }

    /**
     * Stores a key-value pair in the hash table.
     * If the key already exists, updates the existing value.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) {
            if (Objects.equals(entry.key, key)) {
                entry.value = value;
                ++modCount;
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        ++size;
        ++modCount;
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Removes the entry associated with the specified key.
     *
     * @param key the key
     * @return the value, or null if the key was not found
     */
    public V remove(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        Iterator<Entry<K, V>> iterator = table[index].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (Objects.equals(entry.key, key)) {
                --size;
                ++modCount;
                V oldValue = entry.value;
                iterator.remove();
                return oldValue;
            }
        }
        return null;
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key
     * @return the value, or null if the key was not found
     */
    public V get(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        for (Entry<K, V> entry : table[index]) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Updates the value for the specified key.
     * Adds the key-value pair if the key does not exist.
     *
     * @param key   the key
     * @param value the new value
     */
    public void update(K key, V value) {
        put(key, value);
    }

    /**
     * Checks if the specified key exists in the hash table.
     *
     * @param key the key
     * @return true if the key exists, false otherwise
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Computes the hash code for a given key.
     *
     * @param key the key
     * @return the hash code index
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Resizes the hash table.
     */
    private void resize() {
        LinkedList<?>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        size = 0;
        ++modCount;
        for (LinkedList<?> bucket : oldTable) {
            if (bucket != null) {
                for (Entry<K, V> entry : (LinkedList<Entry<K, V>>) bucket) {
                    put(entry.key, entry.value);
                }
            }
        }
    }

    /**
     * Returns an iterator for iterating over the entries in the hash table.
     * Throws ConcurrentModificationException if the hash table is modified during iteration.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int index = 0;
            private Iterator<Entry<K, V>> bucketIterator = null;
            private Entry<K, V> lastReturned = null;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < table.length) {
                    if (bucketIterator != null && bucketIterator.hasNext()) {
                        return true;
                    }
                    if (table[index] != null) {
                        bucketIterator = table[index].iterator();
                    }
                    ++index;
                }
                return false;
            }

            @Override
            public Map.Entry<K, V> next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = bucketIterator.next();
                return lastReturned;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException("The next method has not yet been called,"
                                                    + " or the remove method has already been "
                                                    + "called after the last call"
                                                    + " to the next method");
                }
                HashTable.this.remove(lastReturned.getKey());
                lastReturned = null;
            }
        };
    }

    /**
     * Checks if this hash table is equal to another hash table.
     *
     * @param o the object
     * @return true if the tables are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashTable<?, ?> that = (HashTable<?, ?>) o;
        if (size != that.size) {
            return false;
        }
        for (Map.Entry<K, V> entry : this) {
            boolean found = false;
            for (LinkedList<? extends Entry<?, ?>> bucket : that.table) {
                if (bucket != null) {
                    for (Entry<?, ?> otherEntry : bucket) {
                        if (Objects.equals(entry.getKey(), otherEntry.getKey())) {
                            if (!Objects.equals(entry.getValue(), otherEntry.getValue())) {
                                return false;
                            }
                            found = true;
                            break;
                        }
                    }
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes the hash code for this hash table based on the entries it contains.
     *
     * @return the computed hash code for this hash table
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    hash += entry.hashCode();
                }
            }
        }
        return hash;
    }

    /**
     * Returns a string representation of the hash table.
     *
     * @return a string representing the hash table
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    sb.append(entry.toString()).append(", ");
                }
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Inner class for representing key-value pairs.
     */
    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key)
                   && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}