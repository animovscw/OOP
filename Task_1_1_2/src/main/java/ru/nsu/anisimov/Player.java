package ru.nsu.anisimov;

import java.util.ArrayList;

import ru.nsu.anisimov.Card;
import ru.nsu.anisimov.Rank;

public class Player {
    final ArrayList<Card> cards;
    int sum;
    int countAces;
    int firstAce = -1;

    public Player() {
        cards = new ArrayList<>();
        sum = 0;
        countAces = 0;
    }

    public void addCard(Card card) {
        cards.add(card);

        sum += card.getValue();
        if (card.getRank() == Rank.ACE) {
            ++countAces;
            if (countAces > 1) {
                sum -= 10;
                card.checkSum();
            }

            if (firstAce == -1) {
                firstAce = cards.size() - 1;
            }
        }
        if (firstAce != -1 && cards.get(firstAce).getValue() != 1 && sum > 21) {
            sum-=10;
            cards.get(firstAce).checkSum();
        }
    }
}
