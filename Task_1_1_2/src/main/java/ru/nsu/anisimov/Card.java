package ru.nsu.anisimov;

public class Card {
    public String suit;
    public String rank;
    public int value;
    public boolean isCardHidden = false;

    public Card(String rank, String suit, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }
}