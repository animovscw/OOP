package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The class represents a deck of playing cards in a game. The deck contains 52 cards,
 * one for each combination of rank and suit.
 */
public class Deck {

    /**
     * The deck of cards represented as list of card objects.
     */
    private final ArrayList<Card> deck;

    /**
     * Constructs a new deck of 52 cards. The deck is shuffled after being created.
     */
    public Deck() {
        deck = new ArrayList<>();
        Rank[] ranks = new Rank[]{
            Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX,
            Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK,
            Rank.QUEEN, Rank.KING, Rank.ACE
        };
        Suit[] suits = new Suit[]{
            Suit.HEART, Suit.DIAMOND, Suit.CLUB, Suit.SPADE
        };
        for (Suit suit : suits) {
            for (Rank rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }

        // Shuffle the deck.
        shuffle();
    }

    /**
     * Shuffles the deck of cards using
     * the {@link Collections#shuffle} method to randomize the order.
     */
    private void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Removes and returns the top card from th deck.
     *
     * @return the top card from the deck.
     */
    public Card getCard() {
        return deck.removeFirst();
    }
}