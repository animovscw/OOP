package ru.nsu.anisimov;

import java.util.Scanner;

/**
 * Class representing the game logic for a Blackjack game.
 */
public class Main {
    int round = 1;
    int countPlayerWins = 0;
    int countDealerWins = 0;

    /**
     * Handles the dealer's win scenario.
     * Increments the dealer's win count and displays the result of the round.
     */
    void dealerWin() {
        ++countDealerWins;
        displayRoundResult("Вы проиграли раунд!");
        ++round;
    }

    /**
     * Handles the player's win scenario.
     * Increments the player's win count and displays the result of the round.
     */
    void playerWin() {
        ++countPlayerWins;
        displayRoundResult("Вы выиграли раунд!");
        ++round;
    }

    /**
     * Checks if the player has a Blackjack (sum = 21).
     *
     * @param player player
     * @return true if the player has Blackjack, false otherwise.
     */
    boolean checkBlackjack(Player player) {
        return player.getHandSize() == 2 && player.getSum() == 21;
    }

    /**
     * Displays the result of the round, including the current score between the player and dealer.
     *
     * @param message describing the result of the round.
     */
    private void displayRoundResult(String message) {
        System.out.print(message + " Счёт " + countPlayerWins + ":" + countDealerWins + " ");
        if (countPlayerWins > countDealerWins) {
            System.out.println("в вашу пользу.");
        } else if (countPlayerWins < countDealerWins) {
            System.out.println("в пользу дилера.");
        } else {
            System.out.println("равный.");
        }
        System.out.println();
    }

    /**
     * Determines the winner of the round based on the player's and dealer's hand sums.
     *
     * @param player player
     * @param dealer AI player
     */
    void checkForWin(Player player, Player dealer) {
        if (dealer.getSum() > 21) {
            playerWin();
        } else if (player.getSum() <= 21 && dealer.getSum() <= 21) {
            if (player.getSum() > dealer.getSum()) {
                playerWin();
            } else if (player.getSum() < dealer.getSum()) {
                dealerWin();
            } else {
                displayRoundResult("Ничья!");
            }
        }
    }

    /**
     * Displays the cards of the player and the dealer.
     *
     * @param player player
     * @param dealer AI player
     */
    void showCards(Player player, Player dealer) {
        System.out.print("\tВаши карты: " + getFormattedHand(player));
        System.out.println(" => " + player.getSum());
        System.out.print("\tКарты дилера: " + getFormattedHand(dealer));
        if (!dealer.getHidden()) {
            System.out.println(" => " + dealer.getSum());
        } else {
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Formats the player's hand for display purposes.
     *
     * @param player player
     * @return A string representing the formatted hand.
     */
    private String getFormattedHand(Player player) {
        StringBuilder hand = new StringBuilder("[");
        for (int index = 0; index < player.getHandSize(); ++index) {
            hand.append(player.getCardByIndex(index));
            if (index < player.getHandSize() - 1) {
                hand.append(", ");
            }
        }
        hand.append("]");
        return hand.toString();
    }

    /**
     * The main method that starts and controls the game loop for Blackjack.
     * The game will keep running indefinitely until stopped manually.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Main game = new Main();
        System.out.println("Добро пожаловать в Блэкджек!");
        Scanner scan = new Scanner(System.in);
        while (true) {
            Deck deck = new Deck();
            Player player = new Player();
            Player dealer = new Player();
            player.addCard(deck);
            player.addCard(deck);
            dealer.addCard(deck);
            dealer.addCard(deck);
            dealer.getCardByIndex(dealer.getHandSize() - 1).setHiddenCard(true);
            dealer.setHiddenPlayer(true);
            System.out.println("Раунд " + game.round);
            System.out.println("Дилер раздал карты");
            game.showCards(player, dealer);
            if (game.checkBlackjack(player)) {
                game.playerWin();
                continue;
            }
            System.out.print("""
                    Ваш ход
                    -------
                    """);
            int action;
            while (true) {
                if (player.getSum() > 21) {
                    game.dealerWin();
                    break;
                }
                System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться");
                action = scan.nextInt();
                if (action == 1) {
                    player.addCard(deck);
                    System.out.print("Вы открыли карту ");
                    player.getCardByIndex(player.getHandSize() - 1).print();
                    System.out.println();
                    game.showCards(player, dealer);
                    if (player.getSum() == 21) {
                        game.playerWin();
                    }
                } else if (action != 0) {
                    return;
                } else {
                    break;
                }
            }
            if (player.getSum() > 21) {
                continue;
            }
            System.out.print("""
                    Ход Дилера
                    -------
                    """);
            dealer.getCardByIndex(dealer.getHandSize() - 1).setHiddenCard(false);
            dealer.setHiddenPlayer(false);
            System.out.print("Дилер открывает закрытую карту ");
            dealer.getCardByIndex(dealer.getHandSize() - 1).print();
            System.out.println();
            game.showCards(player, dealer);
            if (game.checkBlackjack(dealer)) {
                game.dealerWin();
                continue;
            }
            while (dealer.getSum() < 17) {
                dealer.addCard(deck);
                System.out.print("Дилер открыл карту ");
                dealer.getCardByIndex(dealer.getHandSize() - 1).print();
                System.out.println();
                game.showCards(player, dealer);
            }
            game.checkForWin(player, dealer);
            System.out.println();
        }
    }
}
