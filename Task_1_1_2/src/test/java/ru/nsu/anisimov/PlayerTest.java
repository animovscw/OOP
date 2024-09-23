package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Deck deck;
    private Player player;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        player = new Player();
    }

    @Test
    void testSetHiddenPlayer() {
        player.setHiddenPlayer(true);
        Assertions.assertTrue(player.getHidden());

        player.setHiddenPlayer(false);
        Assertions.assertFalse(player.getHidden());
    }


    @Test
    void testGetHandSize() {
        player.addCard(deck);
        player.addCard(deck);

        Assertions.assertTrue(player.getSum() < 22);
        Assertions.assertEquals(2, player.getHandSize());
    }

    @Test
    void getAceTest() {
        int aceCount = 0;
        Player player = new Player();
        Deck deck = new Deck();

        while (aceCount < 2) {
            player.addCard(deck);
            if (player.getCardByIndex(player.getHandSize() - 1).getRank() == Rank.ACE) {
                ++aceCount;
            }
        }

        for (int i = 0; i < player.getHandSize(); ++i) {
            Card card = player.getCardByIndex(i);
            if (card.getRank() == Rank.ACE) {
                Assertions.assertEquals(1, card.getValue(), "Значение туза должно быть 1");
            }
        }
    }
}