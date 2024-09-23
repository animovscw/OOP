package ru.nsu.anisimov;

import java.util.ArrayList;

public class Player {
    private int sum = 0;
    private final ArrayList<Card> hand = new ArrayList<>();
    private final ArrayList<Integer> aceIndex = new ArrayList<>();
    private boolean hidden = false;

    public void addCard(Deck deck) {
        Card card = deck.getCard();
        hand.add(card);

        if (card.getRank() == Rank.ACE) {
            aceIndex.add(hand.size() - 1);
        }

        sum += card.getValue();
        deck.removeCard();

        if (sum > 21 && !aceIndex.isEmpty()) {
            for (Integer index : aceIndex) {
                if (hand.get(index).getValue() == 11) {
                    hand.get(index).lowValue();
                    sum -= 10;
                    break;
                }
            }
        }
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHiddenPlayer(boolean value) {
        hidden = value;
    }

    public Card getCardByIndex(int index) {
        return hand.get(index);
    }

    public int getSum() {
        return sum;
    }

    public int getHandSize() {
        return hand.size();
    }
}
