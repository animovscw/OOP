package ru.nsu.anisimov;

import java.util.*;

public class HashTable<K, V> implements Iterable<Map.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final float LOAD_FACTOR = 0.75f;

    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private int modCount;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int initialCapacity) {
        table = new LinkedList[initialCapacity];
        size = 0;
        modCount = 0;
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;

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
            this.value = value;
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            ;
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key) &&
                   Objects.equals(value, entry.value);
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

    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % table.length;
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
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

    public void remove(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return;
        }

        Iterator<Entry<K, V>> iterator = table[index].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.key.equals(key)) {
                iterator.remove();
                --size;
                ++modCount;
                return;
            }
        }
    }

    public V get(K key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public void update(K key, V value) {
        put(key, value);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int index = 0;
            private Iterator<Entry<K, V>> bucketIterator = null;

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
                return bucketIterator.next();
            }
        };
    }

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

    private void resize() {
        LinkedList<Entry<K, V>>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        size = 0;
        ++modCount;

        for (LinkedList<Entry<K, V>> bucket : oldTable) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    put(entry.key, entry.value);
                }
            }
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        ++modCount;
    }

    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();

        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one"));
    }
}
