package ru.nsu.anisimov;

import java.util.Iterator;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Assertions.assertEquals(1, hashTable.get("one"));
        Assertions.assertEquals(2, hashTable.get("two"));
    }

    @Test
    void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Assertions.assertEquals(1, hashTable.remove("one"));
        Assertions.assertNull(hashTable.get("one"));
        Assertions.assertEquals(2, hashTable.get("two"));
    }

    @Test
    void testRemoveNonExistentKey() {
        hashTable.put("one", 1);

        Assertions.assertNull(hashTable.remove("two"));
    }

    @Test
    void testGetNonExistentKey() {
        Assertions.assertNull(hashTable.get("none"));
    }

    @Test
    void testUpdate() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.update("one", 3);

        Assertions.assertEquals(3, hashTable.get("one"));
    }

    @Test
    void testUpdateNewKey() {
        hashTable.update("three", 3);

        Assertions.assertEquals(3, hashTable.get("three"));
    }

    @Test
    void testContainsKey() {
        hashTable.put("one", 1);

        Assertions.assertTrue(hashTable.containsKey("one"));
        Assertions.assertFalse(hashTable.containsKey("two"));
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; ++i) {
            hashTable.put("key" + i, i);
        }
        for (int i = 0; i < 20; ++i) {
            Assertions.assertEquals(i, hashTable.get("key" + i));
        }
    }

    @Test
    void testEquality() {
        HashTable<String, Integer> anotherTable = new HashTable<>();
        hashTable.put("one", 1);
        anotherTable.put("one", 1);

        Assertions.assertEquals(hashTable, anotherTable);

        anotherTable.put("two", 2);

        Assertions.assertNotEquals(hashTable, anotherTable);
    }

    @Test
    void testToString() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        String result = hashTable.toString();

        Assertions.assertTrue(result.contains("one=1"));
        Assertions.assertTrue(result.contains("two=2"));
    }

    @Test
    void testPutDuplicateKey() {
        hashTable.put("one", 1);
        hashTable.put("one", 2);

        Assertions.assertEquals(2, hashTable.get("one"));
    }

    @Test
    void testIterator() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        Iterator<Map.Entry<String, Integer>> iterator = hashTable.iterator();

        Assertions.assertTrue(iterator.hasNext());

        Map.Entry<String, Integer> entry = iterator.next();

        Assertions.assertTrue(entry.getKey().equals("one") || entry.getKey().equals("two"));
        Assertions.assertEquals(1, entry.getValue());
        Assertions.assertTrue(iterator.hasNext());

        entry = iterator.next();

        Assertions.assertTrue(entry.getKey().equals("one") || entry.getKey().equals("two"));
        Assertions.assertEquals(2, entry.getValue());
    }

    @Test
    void testNullValue() {
        hashTable.put("one", null);
        Assertions.assertNull(hashTable.get("one"));
    }
}