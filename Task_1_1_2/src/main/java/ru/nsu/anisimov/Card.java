package ru.nsu.anisimov;

/**
 * The class represents a playing card with a suit, rank, and value.
 */
public class Card {
    private final String suit;
    private final Rank rank;
    private int value;
    private boolean hidden = false;

    /**
     * Constructs a card with the specified rank and suit.
     *
     * @param rank the rank of the card.
     * @param suit the suit of the card.
     */
    public Card(Rank rank, String suit) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.getValue();
    }

    /**
     * Returns the value of the card.
     *
     * @return the value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card.
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Sets the visibility of the card.
     *
     * @param hidden true if the card should be hidden, false otherwise.
     */
    public void setHiddenCard(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Checks if the card is hidden.
     *
     * @return true if the card is hidden, false otherwise.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Adjusts the value of the card if it is an Ace,
     * changing it to 1 if it was previously counted as 11.
     */
    public void lowValue() {
        if (rank == Rank.ACE) {
            value = 1;
        }
    }

    /**
     * Prints the card's details, including its rank, suit, and value.
     * If the card is hidden, it prints a placeholder.
     */
    public void display() {
        if (hidden) {
            System.out.print("<закрытая карта>");
        } else {
            System.out.print(rank.getRankName() + " " + suit + " (" + value + ")");
        }
    }

    /**
     * Returns a string representation of the card,
     * showing its rank, suit, and value if it is visible.
     *
     * @return a string representation of the card.
     */
    @Override
    public String toString() {
        if (hidden) {
            return "<закрытая карта>";
        } else {
            return rank.getRankName() + " " + suit + " (" + value + ")";
        }
    }
}
