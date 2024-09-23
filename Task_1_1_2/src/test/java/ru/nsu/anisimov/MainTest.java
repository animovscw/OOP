package ru.nsu.anisimov;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MainTest {

    private String runGame(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        InputStream inputStream = System.in;
        PrintStream outputStream = System.out;
        Main.main(null);
        System.setIn(inputStream);
        System.setOut(outputStream);
        return myOut.toString();
    }

    @Test
    void testImmediateStop() {
        String output = runGame("-1\n");
        String subOutput = output.substring(0, 28);
        Assertions.assertEquals("Добро пожаловать в Блэкджек!", subOutput);
    }

    @Test
    void testInput() {
        String output = runGame("0\n0\n0\n0\n0\n2\n");
        String subOutput = output.substring(output.length() - 58);
        Assertions.assertEquals("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться\n",
                subOutput);
    }

    @Test
    void testBlackjack() {
        Main game = new Main();
        Deck deck = new Deck();
        Player player = new Player();

        player.addCard(deck);
        player.addCard(deck);
        if (player.getSum() == 21) {
            Assertions.assertTrue(game.checkBlackjack(player));
        } else {
            Assertions.assertFalse(game.checkBlackjack(player));
        }
    }

    @Test
    void testPlayerWin() {
        Main game = new Main();

        Assertions.assertEquals(0, game.countPlayerWins);

        game.playerWin();

        Assertions.assertEquals(1, game.countPlayerWins);
        Assertions.assertEquals(2, game.round);
    }

    @Test
    void testDealerWin() {
        Main game = new Main();

        game.dealerWin();
        game.dealerWin();

        Assertions.assertEquals(2, game.countDealerWins);
        Assertions.assertEquals(3, game.round);
    }

    @Test
    void testCheckForWin() {
        Player player = new Player();
        Player dealer = new Player();
        Deck deck = new Deck();

        player.addCard(deck);
        player.addCard(deck);
        dealer.addCard(deck);
        dealer.addCard(deck);
        Main game = new Main();
        int rounds = game.countPlayerWins;
        game.checkForWin(player, dealer);
        Assertions.assertTrue(game.countPlayerWins >= rounds);
    }
}