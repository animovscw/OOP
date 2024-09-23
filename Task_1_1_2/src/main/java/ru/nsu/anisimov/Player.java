package ru.nsu.anisimov;

import java.util.ArrayList;

/**
 * Represents a player in the game of Blackjack.
 * The player has a hand of cards and keeps track of their total value.
 */
public class Player {
    private int sum = 0;
    private final ArrayList<Card> hand = new ArrayList<>();
    private final ArrayList<Integer> aceIndex = new ArrayList<>();
    private boolean hidden = false;

    /**
     * Adds a card from the deck to the player's hand.
     * If the card is an Ace, it is tracked for value adjustment.
     * If the total value exceeds 21, it adjusts the value of Aces as necessary.
     *
     * @param deck the deck from which to draw the card.
     */
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

    /**
     * Checks if the player's hand is hidden.
     *
     * @return true if the player's hand is hidden, false otherwise.
     */
    public boolean getHidden() {
        return hidden;
    }

    /**
     * Sets the visibility of the player's hand.
     *
     * @param value true to hide the hand, false to reveal it.
     */
    public void setHiddenPlayer(boolean value) {
        hidden = value;
    }

    /**
     * Retrieves a card from the player's hand by index.
     *
     * @param index the index of the card in the hand.
     * @return the card at the specified index.
     */
    public Card getCardByIndex(int index) {
        return hand.get(index);
    }

    /**
     * Gets the total value of the player's hand.
     *
     * @return the total value of the hand.
     */
    public int getSum() {
        return sum;
    }

    /**
     * Gets the number of cards in the player's hand.
     *
     * @return the size of the player's hand.
     */
    public int getHandSize() {
        return hand.size();
    }
}
