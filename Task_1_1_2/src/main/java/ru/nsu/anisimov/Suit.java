package ru.nsu.anisimov;

public enum Suit {
    HEART("Червы"),
    DIAMOND("Бубны"),
    CLUB("Трефы"),
    SPADE("Пики");

    private String suitType;

    Suit(String suitType) {
        this.suitType = suitType;
    }

    @Override
    public String toString() {
        return suitType;
    }
}
