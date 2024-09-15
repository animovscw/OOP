package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    final ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        Rank[] ranks = new Rank[]{
            Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX,
            Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK,
            Rank.QUEEN, Rank.KING, Rank.ACE
        };
        Suit[] suits = new Suit[]{Suit.HEART, Suit.DIAMOND, Suit.CLUB, Suit.SPADE};
        for (Suit suit : suits) {
            for (Rank rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public Card takeCard() {
        return cards.removeFirst();
    }
}
