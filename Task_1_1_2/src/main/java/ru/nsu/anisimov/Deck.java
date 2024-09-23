package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> deck;

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
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(deck);
    }

    public void removeCard() {
        deck.removeFirst();
    }

    public Card getCard() {
        return deck.getFirst();
    }

    public int getSize() {
        return deck.size();
    }
}