package ru.nsu.anisimov;

import java.util.Hashtable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ru.nsu.anisimov.Main.main;

class MainTest {
    @Test
    public void test() {
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("one", 1);
        Assertions.assertEquals(1,hashtable.get("one"));
    }
}