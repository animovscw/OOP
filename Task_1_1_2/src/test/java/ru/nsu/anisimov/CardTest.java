package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void correctRecognitionOfHighRankCard() {
        Card card = new Card(Rank.KING, "Бубны");
        Assertions.assertEquals(card.getRank(), Rank.KING);
        Assertions.assertEquals(card.getSuit(), "Бубны");
        Assertions.assertEquals(10, card.getValue());
    }

    @Test
    void correctRecognitionOfLowRankCard() {
        Card card = new Card(Rank.FIVE, "Трефы");
        Assertions.assertEquals(card.getRank(), Rank.FIVE);
        Assertions.assertEquals(card.getSuit(), "Трефы");
        Assertions.assertEquals(5, card.getValue());
    }

    @Test
    void wrongRecognitionOfHighRankCard() {
        Card card = new Card(Rank.ACE, "Червы");
        Assertions.assertNotEquals(card.getRank(), Rank.QUEEN);
        Assertions.assertNotEquals(card.getSuit(), "Бубны");
        Assertions.assertNotEquals(10, card.getValue());
    }

    @Test
    void wrongRecognitionOfLowRankCard() {
        Card card = new Card(Rank.TWO, "Трефы");
        Assertions.assertNotEquals(card.getRank(), Rank.THREE);
        Assertions.assertNotEquals(card.getSuit(), "Червы");
        Assertions.assertNotEquals(4, card.getValue());
    }
}
