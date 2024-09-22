package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class PlayerTest {
    @Test
    void getCardTest() {
        Player player = new Player();
        Assertions.assertTrue(player.isAllCardsOpen());
        Assertions.assertTrue(player.hand.isEmpty());
        Assertions.assertFalse(player.getSum() != 0);
    }
}