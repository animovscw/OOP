package ru.nsu.anisimov;

/**
 * The enum represents the rank of a card in a deck,including numbered cards and their face.
 */
public enum Rank {

    /**
     * The Ace card with a value of 11.
     */
    ACE("Туз", 11),

    /**
     * The King card with a value of 10.
     */
    KING("Король", 10),

    /**
     * The Queen card with a value of 10.
     */
    QUEEN("Дама", 10),

    /**
     * The Jack card with a value of 10.
     */
    JACK("Валет", 10),

    /**
     * The Ten card with a value of 10.
     */
    TEN("Десятка", 10),

    /**
     * The Nine card with a value of 9.
     */
    NINE("Девятка", 9),

    /**
     * The Eight card with a value of 8.
     */
    EIGHT("Восьмерка", 8),

    /**
     * The Seven card with a value of 7.
     */
    SEVEN("Семерка", 7),

    /**
     * The Six card with a value of 6.
     */
    SIX("Шестерка", 6),

    /**
     * The Five card with a value of 5.
     */
    FIVE("Пятерка", 5),

    /**
     * The Four card with a value of 4.
     */
    FOUR("Четверка", 4),

    /**
     * The Three card with a value of 3.
     */
    THREE("Тройка", 3),

    /**
     * The Two card with a value of 2.
     */
    TWO("Двойка", 2);

    /**
     * The name of the rank.
     */
    private final String rankName;

    /**
     * The value of the rank.
     */
    private final int rankValue;

    /**
     * Constructs a rank with the given name and value.
     *
     * @param rankName  the name of the rank
     * @param rankValue the value of the rank
     */
    Rank(String rankName, int rankValue) {
        this.rankName = rankName;
        this.rankValue = rankValue;
    }

    /**
     * Returns the value of the rank, which is used for score calculation.
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
    @Override
    public String toString() {
        return rankName;
    }
}