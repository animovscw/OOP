package ru.nsu.anisimov;

/**
 * The class represents a playing card with a suit and rank.
 * The card can either be open or closed.
 */
public class Card {

    /**
     * The suit of the card.
     */
    private final Suit suit;

    /**
     * The rank of the card.
     */
    private final Rank rank;

    /**
     * The value of the card, which is derived from its rank.
     */
    private int value;

    /**
     * Indicates whether the card is open or closed.
     */
    private boolean isCardOpen;

    /**
     * Constructs a new card with the specified suit and rank. In the beginning, it is closed.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        value = this.rank.getValue();
        this.isCardOpen = false;
    }

    /**
     * Returns the current value of the card.
     *
     * @return the value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns whether the card is currently open.
     *
     * @return true if the card is open, otherwise false
     */
    public boolean isOpen() {
        return isCardOpen;
    }

    /**
     * Sets the card to be open.
     */
    public void open() {
        isCardOpen = true;
    }

    /**
     * Lowers the value of the card if it is not a first Ace in the hand.
     * Changes its value to 1.
     */
    public void lowValue() {
        if (rank == Rank.ACE) {
            this.value = 1;
        }
    }

    /**
     * Returns a string representation of the card.
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        if (!isCardOpen) {
            return "<закрытая карта>";
        } else {
            return rank.toString() + " " + suit.toString() + " (" + value + ")";
        }
    }
}