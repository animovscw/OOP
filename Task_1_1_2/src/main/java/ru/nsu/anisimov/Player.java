package ru.nsu.anisimov;

import java.util.ArrayList;

/**
 * The class represents a player in the game. Each player has a hand of cards.
 */
public class Player {

    /**
     * The sum of the card values in the player's hand.
     */
    private int sum = 0;

    /**
     * The count of Aces in the player's hand.
     */
    private int countAcesInHand = 0;

    /**
     * Index of the first Ace card in the hand.
     */
    private int isFirstAce = 0;

    /**
     * The player's hand of cards.
     */
    ArrayList<Card> hand = new ArrayList<>();

    /**
     * Adds a card to the player's hand, updating the sum of the card values.
     *
     * @param card the card to add to the player's hand
     */
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

    /**
     * Shows all cards in the player's hand. If all cards are open,
     * it also displays the total value.
     *
     * @return a string representation of the player's hand and, by chance, total value
     */
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

    /**
     * Checks if all cards in the player's hand are open.
     *
     * @return true if all cards are open, otherwise false
     */
    public boolean isAllCardsOpen() {
        for (Card card : hand) {
            if (!card.isOpen()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the sum of the values of the cards in the player's hand.
     *
     * @return the total value of the player's hand
     */
    public int getSum() {
        return sum;
    }

    /**
     * Shows the last card added to the player's hand.
     *
     * @return a string representation of the last card in the hand
     */
    public String showLastCard() {
        return hand.getLast().toString();
    }

    /**
     * Opens the last card at the specified index in the player's hand.
     *
     * @param index the index of the card to open
     */
    public void openCard(int index) {
        hand.get(index).open();
    }

    /**
     * Opens the last card added to the player's hand.
     */
    public void openLastCard() {
        hand.getLast().open();
    }

    /**
     * Returns the count of cards in the player's hand.
     *
     * @return the number of cards in the player's hand
     */
    public int getCardCount() {
        return hand.size();
    }
}