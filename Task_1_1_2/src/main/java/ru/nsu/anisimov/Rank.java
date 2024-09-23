package ru.nsu.anisimov;

/**
 * Represents the ranks of the playing cards in the game.
 * Each rank has a corresponding name and value.
 */
public enum Rank {

    ACE("Ace", 11),
    KING("King", 10),
    QUEEN("Queen", 10),
    JACK("Jack", 10),
    TEN("Ten", 10),
    NINE("Nine", 9),
    EIGHT("Eight", 8),
    SEVEN("Seven", 7),
    SIX("Six", 6),
    FIVE("Five", 5),
    FOUR("Four", 4),
    THREE("Three", 3),
    TWO("Two", 2);

    private final String rankName;
    private final int rankValue;

    /**
     * Constructs a rank with the specified name and value.
     *
     * @param rankName the name of the rank.
     * @param rankValue the value of the rank.
     */
    Rank(String rankName, int rankValue) {
        this.rankName = rankName;
        this.rankValue = rankValue;
    }

    /**
     * Returns the value of the rank.
     *
     * @return the value of the rank.
     */
    public int getValue() {
        return rankValue;
    }

    /**
     * Returns the name of the rank.
     *
     * @return the name of the rank.
     */
    public String getRankName() {
        return rankName;
    }
}
