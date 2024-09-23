package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeckTest {
    @Test
    void testDeckSize() {
        Deck deck = new Deck();
        Assertions.assertEquals(52, deck.getSize());
    }

    @Test
    void testDeckRemove() {
        Deck deck = new Deck();
        deck.removeCard();
        deck.removeCard();
        Assertions.assertEquals(50, deck.getSize());
    }

    @Test
    void testWrongSize() {
        Deck deck = new Deck();
        Assertions.assertFalse(deck.getSize() != 52);
    }
}