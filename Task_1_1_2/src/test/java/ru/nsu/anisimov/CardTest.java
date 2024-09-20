package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void correctRecognitionOfHighRankCard() {
        Card card = new Card(Suit.DIAMOND, Rank.KING);
        Assertions.assertEquals(card.getRank(), Rank.KING);
        Assertions.assertEquals(card.getSuit(), Suit.DIAMOND);
        Assertions.assertEquals(10, card.getValue());
    }

    @Test
    void correctRecognitionOfLowRankCard() {
        Card card = new Card(Suit.SPADE, Rank.FIVE);
        Assertions.assertEquals(card.getRank(), Rank.FIVE);
        Assertions.assertEquals(card.getSuit(), Suit.SPADE);
        Assertions.assertEquals(5, card.getValue());
    }

    @Test
    void wrongRecognitionOfHighRankCard() {
        Card card = new Card(Suit.HEART, Rank.ACE);
        Assertions.assertNotEquals(card.getRank(), Rank.QUEEN);
        Assertions.assertNotEquals(card.getSuit(), Suit.DIAMOND);
        Assertions.assertNotEquals(10, card.getValue());
    }

    @Test
    void wrongRecognitionOfLowRankCard() {
        Card card = new Card(Suit.CLUB, Rank.TWO);
        Assertions.assertNotEquals(card.getRank(), Rank.THREE);
        Assertions.assertNotEquals(card.getSuit(), Suit.HEART);
        Assertions.assertNotEquals(4, card.getValue());
    }
}