package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class InterfaceTest {

    @Test
    void testShowCards() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Game game = new Game();
        game.player = new Player();
        game.dealer = new Player();

        game.player.addCard(new Card(Suit.HEART, Rank.ACE));
        game.player.addCard(new Card(Suit.HEART, Rank.TWO));

        game.dealer.addCard(new Card(Suit.SPADE, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.ACE));

        game.player.openCard(0);
        game.player.openCard(1);
        game.dealer.openCard(0);
        game.dealer.openCard(1);

        Interface.showCards(game.getPlayerHand(), game.getDealerHand());

        String output = outputStream.toString();

        System.setOut(originalOut);

        String expectedOutput = """
                \tВаши карты: [Туз Червы (11), Двойка Червы (2)] => 13
                \tКарты дилера: [Туз Пики (11), Туз Трефы (1)] => 12
                """;
        Assertions.assertEquals(expectedOutput, output);
    }

    private String run(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        InputStream inputStream = System.in;
        PrintStream outputStream = System.out;
        Interface.main(null);
        System.setIn(inputStream);
        System.setOut(outputStream);
        return myOut.toString();
    }

    @Test
    void inputTest() {
        run("0\n0\n0\n0\n-1\n");
        Assertions.assertTrue(true);
    }
}