package ru.nsu.anisimov;

import java.util.ArrayList;

public class Player {
    public boolean isCardHidden = false;
    int sum = 0;
    ArrayList<Card> hand = new ArrayList<Card>();
    ArrayList<Integer> listOfAces = new ArrayList<Integer>();

    public void getCard(Deck deck) {
        hand.add(deck.deck.getFirst());
        if (hand.getLast().rank.equals("Туз")) {
            listOfAces.add(hand.size() - 1);
        }
        sum += hand.getLast().value;
        deck.removeCard();
        if (sum > 21 && !listOfAces.isEmpty()) {
            for (Integer index : listOfAces) {
                hand.get(index).value = 1;
            }
            sum -= 10;
        }
    }
}