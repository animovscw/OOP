package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class PlayerTest {
    @Test
    void getCardTest() {
        Player player = new Player();
        Assertions.assertTrue(player.isAllCardsOpen());
        Assertions.assertTrue(player.hand.isEmpty());
        Assertions.assertFalse(player.getSum() != 0);
    }
}