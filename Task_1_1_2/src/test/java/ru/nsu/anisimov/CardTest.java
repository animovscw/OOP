package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void correctRecognitionOfHighRankCard() {
        Card card = new Card(Suit.DIAMOND, Rank.KING);
        Assertions.assertTrue(card.getRank().equals(Rank.KING));
        Assertions.assertTrue(card.getSuit().equals(Suit.DIAMOND));
        Assertions.assertTrue(card.getValue() == 10);
    }

    @Test
    void correctRecognitionOfLowRankCard() {
        Card card = new Card(Suit.SPADE, Rank.FIVE);
        Assertions.assertTrue(card.getRank().equals(Rank.FIVE));
        Assertions.assertTrue(card.getSuit().equals(Suit.SPADE));
        Assertions.assertTrue(card.getValue() == 5);
    }

    @Test
    void wrongRecognitionOfHighRankCard() {
        Card card = new Card(Suit.HEART, Rank.ACE);
        Assertions.assertFalse(card.getRank().equals(Rank.QUEEN));
        Assertions.assertFalse(card.getSuit().equals(Suit.DIAMOND));
        Assertions.assertFalse(card.getValue() == 10);
    }

    @Test
    void wrongRecognitionOfLowRankCard() {
        Card card = new Card(Suit.CLUB, Rank.TWO);
        Assertions.assertFalse(card.getRank().equals(Rank.THREE));
        Assertions.assertFalse(card.getSuit().equals(Suit.HEART));
        Assertions.assertFalse(card.getValue() == 4);
    }
}