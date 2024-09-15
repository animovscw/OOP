package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class InterfaceTest {
    @Test
    void practiceTest() {
        String letter = "something to fill the soft deadline";
        String compare = Interface.sendBack("something to fill the soft deadline");
        assertArrayEquals("something to fill the soft deadline.".toCharArray(), compare.toCharArray());
    }
}