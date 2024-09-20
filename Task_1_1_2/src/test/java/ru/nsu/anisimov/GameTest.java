package ru.nsu.anisimov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class GameTest {
    private Game game;

    @BeforeEach
    void setup() {
        game = new Game();
        game.player = new Player();
        game.dealer = new Player();
    }

    @Test
    void testNewRound() {
        game.newRound();

        Assertions.assertTrue(game.isPlayerActed());
        Assertions.assertFalse(game.isEndOfRound());
        Assertions.assertEquals(1, game.getRound());
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

        Assertions.assertEquals(2, game.dealer.getCardCount());

        game.action(1);

        Assertions.assertEquals(3, game.dealer.getCardCount());
    }

    @Test
    void testPlayerBlackjack() {
        game.player.addCard(new Card(Suit.HEART, Rank.ACE));
        game.player.addCard(new Card(Suit.HEART, Rank.KING));

        Assertions.assertTrue(game.playerBlackjack());
        Assertions.assertTrue(game.isEndOfRound());
    }

    @Test
    void testDealerBlackjack() {
        game.dealer.addCard(new Card(Suit.SPADE, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.NINE));

        Assertions.assertTrue(game.dealerBlackjack());
        Assertions.assertTrue(game.isEndOfRound());
    }

    @Test
    void testPlayerHasOverdone() {
        game.player.addCard(new Card(Suit.HEART, Rank.ACE));
        game.player.addCard(new Card(Suit.HEART, Rank.KING));
        game.player.addCard(new Card(Suit.DIAMOND, Rank.KING));

        Assertions.assertTrue(game.playerHasOverdone());
        Assertions.assertTrue(game.isEndOfRound());
    }

    @Test
    void testDealerHasOverdone() {
        game.dealer.addCard(new Card(Suit.HEART, Rank.ACE));
        game.dealer.addCard(new Card(Suit.CLUB, Rank.TEN));
        game.dealer.addCard(new Card(Suit.DIAMOND, Rank.QUEEN));

        Assertions.assertTrue(game.dealerHasOverdone());
        Assertions.assertTrue(game.isEndOfRound());
    }

    @Test
    void testWhoHasBiggerSum_PlayerWins() {
        game.player.addCard(new Card(Suit.SPADE, Rank.NINE));
        game.player.addCard(new Card(Suit.HEART, Rank.QUEEN));

        game.dealer.addCard(new Card(Suit.HEART, Rank.FIVE));
        game.dealer.addCard(new Card(Suit.DIAMOND, Rank.JACK));

        Assertions.assertEquals(1,game.whoHasBiggerSum());
    }

    @Test
    void testWhoHasBiggerSum_DealerWins() {
        game.player.addCard(new Card(Suit.SPADE, Rank.NINE));
        game.player.addCard(new Card(Suit.HEART, Rank.TWO));

        game.dealer.addCard(new Card(Suit.HEART, Rank.JACK));
        game.dealer.addCard(new Card(Suit.DIAMOND, Rank.JACK));

        Assertions.assertEquals(-1,game.whoHasBiggerSum());
    }

    @Test
    void testWhoHasBiggerSum_Draw() {
        game.player.addCard(new Card(Suit.SPADE, Rank.FIVE));
        game.player.addCard(new Card(Suit.HEART, Rank.FIVE));

        game.dealer.addCard(new Card(Suit.CLUB, Rank.TWO));
        game.dealer.addCard(new Card(Suit.DIAMOND, Rank.EIGHT));

        Assertions.assertEquals(0,game.whoHasBiggerSum());
    }
}