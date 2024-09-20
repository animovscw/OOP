package ru.nsu.anisimov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class GameTest {
    private Game game;
    private Deck deck;
    private Player player;
    private Player dealer;

    @BeforeEach
    void setup() {
        game = new Game();
    }

    @Test
    void testNewRound() {
        game.newRound();
        Assertions.assertTrue(game.isPlayerActed());
        Assertions.assertFalse(game.isEndOfRound());
        Assertions.assertEquals(1,game.getRound());
        Assertions.assertFalse(game.getPlayerHand().isEmpty());
        Assertions.assertFalse(game.getDealerHand().isEmpty());
    }
}