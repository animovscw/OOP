package ru.nsu.anisimov;

public enum Rank {

    ACE("Туз", 11),
    KING("Король", 10),
    QUEEN("Дама", 10),
    JACK("Валет", 10),
    TEN("Десятка", 10),
    NINE("Девятка", 9),
    EIGHT("Восьмерка", 8),
    SEVEN("Семерка", 7),
    SIX("Шестерка", 6),
    FIVE("Пятерка", 5),
    FOUR("Четверка", 4),
    THREE("Тройка", 3),
    TWO("Двойка", 2);

    private final String rankName;
    private final int rankValue;

    Rank(String rankName, int rankValue) {
        this.rankName = rankName;
        this.rankValue = rankValue;
    }

    public int getValue() {
        return rankValue;
    }

    public String getRankName() {
        return rankName;
    }
}