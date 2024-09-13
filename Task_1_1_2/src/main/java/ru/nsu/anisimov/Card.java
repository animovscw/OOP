package ru.nsu.anisimov;

public class Card {
    final Suit suit;
    final Rank rank;
    int value;
    boolean cardIsOpen;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        value = this.rank.getValue();
        this.cardIsOpen = false;
    }

    public boolean isCardIsOpen() {
        return cardIsOpen;
    }

    public void openTheCard() {
        cardIsOpen = true;
    }

    public void checkSum() {
        if (rank == Rank.ACE) {
            this.value = 1;
        }
    }

    public int getValue() {
        return value;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        if (!cardIsOpen) {
            return "<закрытая карта>";
        } else {
            return rank + " " + suit + "(" + value + ")";
        }
    }
}
