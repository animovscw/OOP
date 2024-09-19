package ru.nsu.anisimov;

import java.util.ArrayList;


public class Player {
    private int sum = 0;
    private int countAcesInHand = 0;
    private int isFirstAce = 0;
    ArrayList<Card> hand = new ArrayList<>();

    public void addCard(Card card) {
        hand.add(card);
        sum += card.getValue();

        if (card.getRank() == Rank.ACE) {
            ++countAcesInHand;
            if (countAcesInHand > 1) {
                sum -= 10;
                card.lowValue();
            }
            if (isFirstAce == 0) {
                isFirstAce = hand.size() - 1;
            }
        }
        if (sum > 21 && isFirstAce != 0 && hand.get(isFirstAce).getValue() != 1) {
            sum -= 10;
            hand.get(isFirstAce).lowValue();
        }
    }

    public String showCardsInHand() {
        StringBuilder lineResult = new StringBuilder("[");
        for (Card card : hand) {
            lineResult.append(card.toString()).append(", ");
        }
        lineResult = new StringBuilder(lineResult.substring(0, lineResult.length() - 2));
        lineResult.append("]");
        if (isAllCardsOpen()) {
            lineResult.append(" => ");
            lineResult.append(getSum());
        }
        return lineResult.toString();
    }

    public boolean isAllCardsOpen() {
        for (Card card : hand) {
            if (!card.isOpen()) {
                return false;
            }
        }
        return true;
    }

    public int getSum() {
        return sum;
    }

    public String showLastCard() {
        return hand.getLast().toString();
    }

    public void openCard(int index) {
        hand.get(index).open();
    }

    public void openLastCard() {
        hand.getLast().open();
    }
}