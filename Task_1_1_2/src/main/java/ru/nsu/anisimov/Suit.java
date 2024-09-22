package ru.nsu.anisimov;

/**
 * The enum represents the four suits of a deck of a playing cards.
 * Each suit contain its name in Russian.
 */
public enum Suit {

    /**
     * The suit of Hearts.
     */
    HEART("Червы"),

    /**
     * The suit of Diamonds.
     */
    DIAMOND("Бубны"),

    /**
     * The suit of Clubs.
     */
    CLUB("Трефы"),

    /**
     * The suit of Spades.
     */
    SPADE("Пики");

    /**
     * The name of the suit.
     */
    private final String suitType;

    /**
     * Constructs a suit with the given name.
     *
     * @param suitType the name of the suit
     */
    Suit(String suitType) {
        this.suitType = suitType;
    }

    /**
     * Returns the name of the suit.
     *
     * @return the Russian name of the suit.
     */
    @Override
    public String toString() {
        return suitType;
    }
}