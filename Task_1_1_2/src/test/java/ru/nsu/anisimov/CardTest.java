package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void trueRecogniseCard() {
        Card card = new Card("Король", "Бубны", 10);
        Assertions.assertTrue(card.rank.equals("Король"));
        Assertions.assertTrue(card.suit.equals("Бубны"));
        Assertions.assertTrue(card.value == 10);
    }

    @Test
    void wrongRecogniseCard() {
        Card card = new Card("Король", "Пики", 10);
        Assertions.assertFalse(!card.rank.equals("Король"));
        Assertions.assertFalse(card.isCardHidden);
        Assertions.assertFalse(card.value != 10);
    }
}