package ru.nsu.anisimov;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class InterfaceTest {
    private String testRun() {
        ByteArrayInputStream in = new ByteArrayInputStream("0\n0\n0\n0".getBytes());
        System.setIn(in);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        InputStream input = System.in;
        PrintStream output = System.out;
        Interface.main(null);
        System.setIn(input);
        System.setOut(output);
        return out.toString();
    }

    @Test
    void testInput() {
        testRun();
        Assertions.assertTrue(true);
    }

    @Test
    void testBlackjack() {
        Deck deck = new Deck();
        deck.deck.get(0).rank = "Туз";
        deck.deck.get(0).value = 11;
        deck.deck.get(1).rank = "Король";
        deck.deck.get(1).value = 10;
        Player player = new Player();
        player.getCard(deck);
        player.getCard(deck);
        Assertions.assertTrue(Interface.Blackjack(player));
    }

    @Test
    void testWin() {
        Interface.countPlayerWins = 0;
        Interface.round = 0;
        Interface.playerWin();
        Interface.playerWin();

        Assertions.assertTrue(Interface.countPlayerWins == 2);
        Assertions.assertTrue(Interface.round == 2);
    }

    @Test
    void testLoose() {
        Interface.countDealerWins = 0;
        Interface.round = 0;
        Interface.playerLoose();
        Interface.playerLoose();
        Interface.playerLoose();
        Assertions.assertTrue(Interface.countDealerWins == 3);
        Assertions.assertTrue(Interface.round == 3);
    }

    @Test
    void checkWinTest() {
        Player player = new Player();
        Player dealer = new Player();
        player.sum = 21;
        dealer.sum = 21;
        int rounds = Interface.countPlayerWins;
        Interface.checkForTheWin(player, dealer);
        Assertions.assertTrue(Interface.countPlayerWins == rounds);
    }
}