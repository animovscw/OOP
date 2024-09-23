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
            Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN,
            Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE
        };
        String[] suits = new String[]{
            "Червы", "Пики", "Бубны", "Трефы"
        };
        for (String suit : suits) {
            for (Rank rank : ranks) {
                deck.add(new Card(rank, suit));
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
     * Removes the top card from the deck.
     */
    public void removeCard() {
        deck.removeFirst();
    }

    /**
     * Retrieves the top card from the deck without removing it.
     *
     * @return the top card of the deck.
     */
    public Card getCard() {
        return deck.getFirst();
    }

    /**
     * Returns the current number of cards remaining in the deck.
     *
     * @return the size of the deck.
     */
    public int getSize() {
        return deck.size();
    }
}