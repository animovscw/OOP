package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Game game;
    @BeforeEach
    void setup() {
        game = new Game();
    }

    @Test
    void testNewRound() {
        game.newRound();

        Assertions.assertTrue(game.isPlayerActed());
        Assertions.assertFalse(game.isEndOfRound());
        Assertions.assertEquals(3, game.getRound());
        Assertions.assertFalse(game.getPlayerHand().isEmpty());
        Assertions.assertFalse(game.getDealerHand().isEmpty());
    }

    @Test
    void testPlayerActionHit() {
        game.newRound();

        Assertions.assertEquals(2, game.player.getCardCount());

        game.action(1);

        Assertions.assertEquals(3, game.player.getCardCount());
    }

    @Test
    void testDealerActionHit() {
        game.newRound();
        game.action(2);
        game.action(2);
        Assertions.assertEquals(2, game.dealer.getCardCount());

        game.action(1);

        Assertions.assertEquals(3, game.dealer.getCardCount());
    }

    @Test
    void testPlayerBlackjack() {
        game.player = new Player();
        game.player.addCard(new Card(Suit.HEART, Rank.ACE));
        game.player.addCard(new Card(Suit.HEART, Rank.KING));

        Assertions.assertEquals(1, game.checkForTheWin());
        Assertions.assertTrue(game.isEndOfRound());

        game.player.addCard(new Card(Suit.HEART, Rank.TWO));

        Assertions.assertFalse(game.playerBlackjack());
    }

    @Test
    void testDealerBlackjack() {
        game.player = new Player();
        game.dealer = new Player();

        game.player.addCard(new Card(Suit.HEART, Rank.ACE));
        game.player.addCard(new Card(Suit.HEART, Rank.TWO));

        game.dealer.addCard(new Card(Suit.SPADE, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.NINE));

        Assertions.assertEquals(-1, game.checkForTheWin());
        Assertions.assertTrue(game.isEndOfRound());

        game.dealer.addCard(new Card(Suit.HEART, Rank.TWO));

        Assertions.assertFalse(game.dealerBlackjack());
    }

    @Test
    void testPlayerHasOverdone() {
        game.player = new Player();
        game.dealer = new Player();

        game.player.addCard(new Card(Suit.HEART, Rank.ACE));
        game.player.addCard(new Card(Suit.DIAMOND, Rank.TWO));
        game.player.addCard(new Card(Suit.HEART, Rank.TEN));

        game.dealer.addCard(new Card(Suit.SPADE, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.ACE));

        Assertions.assertTrue(game.playerHasOverdone());
        Assertions.assertTrue(game.isEndOfRound());
    }

    @Test
    void testDealerHasOverdone() {
        game.player = new Player();
        game.dealer = new Player();

        game.player.addCard(new Card(Suit.HEART, Rank.ACE));
        game.player.addCard(new Card(Suit.DIAMOND, Rank.ACE));

        game.dealer.addCard(new Card(Suit.SPADE, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.ACE));
        game.dealer.addCard(new Card(Suit.HEART, Rank.TEN));

        Assertions.assertTrue(game.dealerHasOverdone());
        Assertions.assertTrue(game.isEndOfRound());
    }

    @Test
    void testWhoHasBiggerSum_DealerWins() {
        game.player = new Player();
        game.dealer = new Player();

        game.player.addCard(new Card(Suit.SPADE, Rank.NINE));
        game.player.addCard(new Card(Suit.HEART, Rank.TWO));

        game.dealer.addCard(new Card(Suit.HEART, Rank.JACK));
        game.dealer.addCard(new Card(Suit.DIAMOND, Rank.JACK));

        Assertions.assertEquals(-1, game.whoHasBiggerSum());
        Assertions.assertEquals(1, game.getCountDealerWins());
    }

    @Test
    void testWhoHasBiggerSum_Draw() {
        game.player = new Player();
        game.dealer = new Player();

        game.player.addCard(new Card(Suit.SPADE, Rank.FIVE));
        game.player.addCard(new Card(Suit.HEART, Rank.FIVE));

        game.dealer.addCard(new Card(Suit.CLUB, Rank.TWO));
        game.dealer.addCard(new Card(Suit.DIAMOND, Rank.EIGHT));

        Assertions.assertEquals(0, game.whoHasBiggerSum());
    }

    @Test
    void testGetDealerSum() {
        game.dealer = new Player();
        game.dealer.addCard(new Card(Suit.CLUB, Rank.TWO));
        game.dealer.addCard(new Card(Suit.DIAMOND, Rank.EIGHT));

        Assertions.assertEquals(10, game.getDealerSum());
    }

    @Test
    void testGetLastCard_PlayerActed() {
        game.player = new Player();

        game.player.addCard(new Card(Suit.SPADE, Rank.FIVE));
        game.player.addCard(new Card(Suit.HEART, Rank.FIVE));

        game.player.openCard(0);
        game.player.openCard(1);

        Assertions.assertEquals("Пятерка Червы (5)", game.getLastCard(true));
    }

    @Test
    void testGetLastCard_DealerActed() {
        game.dealer = new Player();

        game.dealer.addCard(new Card(Suit.SPADE, Rank.FIVE));
        game.dealer.addCard(new Card(Suit.HEART, Rank.FIVE));

        game.dealer.openCard(0);
        game.dealer.openCard(1);

        Assertions.assertEquals("Пятерка Червы (5)", game.getLastCard(false));


    }
}