package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class HashTableTest {

    private HashTable<String, Integer> table;

    @BeforeEach
    public void setUp() {
        table = new HashTable<>();
    }

    @Test
    public void testToString() {
        table.put("one", 1);
        table.put("two", 2);
        String expected = "{one=1, two=2}";
        Assertions.assertEquals(expected, table.toString());
    }
}