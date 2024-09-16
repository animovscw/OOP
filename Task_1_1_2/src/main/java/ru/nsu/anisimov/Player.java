package ru.nsu.anisimov;

import java.util.ArrayList;

public class Player {
    public boolean isCardHidden = false;
    int points = 0;
    ArrayList<Card> hand = new ArrayList<Card>();
    ArrayList<Integer> listOfAces = new ArrayList<Integer>();

    public void getCard(Deck deck) {
        hand.add(deck.deck.getFirst());
        if (hand.getLast().rank.equals("Туз")) {
            listOfAces.add(hand.size() - 1);
        }
        points += hand.getLast().value;
        deck.removeCard();
        if (points > 21 && !listOfAces.isEmpty()) {
            points -= 10;
            for (Integer index : listOfAces) {
                hand.get(index).value = 1;
            }
        }
    }
}