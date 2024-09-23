package ru.nsu.anisimov;

public class Card {
    private final String suit;
    private final Rank rank;
    private int value;
    private boolean hidden = false;

    public Card(Rank rank, String suit) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.getValue();
    }

    public int getValue() {
        return value;
    }

    public Rank getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setHiddenCard(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void lowValue() {
        if (rank == Rank.ACE) {
            value = 1;
        }
    }

    public void print() {
        if (hidden) {
            System.out.print("<закрытая карта>");
        } else {
            System.out.print(rank.getRankName() + " " + suit + " (" + value + ")");
        }
    }

    @Override
    public String toString() {
        if (hidden) {
            return "<закрытая карта>";
        } else {
            return rank.getRankName() + " " + suit + " (" + value + ")";
        }
    }
}
