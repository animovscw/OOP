package ru.nsu.anisimov;

public class Card {
    private final Suit suit;
    private final Rank rank;
    private int value;
    private boolean isCardOpen;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        value = this.rank.getValue();
        this.isCardOpen = false;
    }

    public int getValue() {
        return value;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isOpen() {
        return isCardOpen;
    }

    public void open() {
        isCardOpen = true;
    }

    public void lowValue() {
        if (rank == Rank.ACE) {
            this.value = 1;
        }
    }

    @Override
    public String toString() {
        if (!isCardOpen) {
            return "<закрытая карта>";
        } else {
            return rank.toString() + " " + suit.toString() + " (" + value + ")";
        }
    }
}