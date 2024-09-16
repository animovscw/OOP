package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        String[] suits = new String[] {
            "Червы", "Пики", "Бубны", "Трефы"
        };
        String[] ranks = new String[] {
            "Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка",
            "Семёрка", "Восьмёрка", "Девятка", "Десятка",
            "Валет", "Дама", "Король", "Туз"
        };
        int[] values = new int[] {
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11
        };
        int index;
        for (String suit : suits) {
            index = 0;
            for (String rank : ranks) {
                deck.add(new Card(rank, suit, values[index]));
                ++index;
            }
        }
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(deck);
    }

    public void removeCard() {
        deck.removeFirst();
    }
}