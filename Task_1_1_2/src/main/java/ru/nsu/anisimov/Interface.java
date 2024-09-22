package ru.nsu.anisimov;

import java.util.Scanner;

/**
 * Main class, it contains commands to play the game.
 */
public class Interface {

    /**
     * function shows players and dealers cards.
     *
     * @param player player
     * @param dealer AI player
     */
    public static void showCards(String player, String dealer) {
        System.out.println("\tВаши карты: " + player);
        System.out.println("\tКарты дилера: " + dealer);
    }

    /**
     * function creates new round.
     *
     * @param blackjack game
     */
    public static void playRound(Game blackjack) {

        blackjack.newRound();
        System.out.println("Раунд " + blackjack.getRound());
        System.out.println("Дилер раздал карты");
        showCards(blackjack.getPlayerHand(), blackjack.getDealerHand());
        System.out.println();

        if (!blackjack.isEndOfRound()) {
            System.out.print("""
                    Ваш ход
                    -------
                    """);
            while (!blackjack.playerBlackjack() && blackjack.isPlayerActed()) {
                System.out.println("Введите “1”, чтобы взять карту, и “0”,"
                        + " чтобы остановиться");
                Scanner command = new Scanner(System.in);
                int action = command.nextInt();
                System.out.println();
                switch (action) {
                    case 1:
                        blackjack.action(action);
                        if (!blackjack.playerBlackjack()) {
                            blackjack.playerHasOverdone();
                        }
                        System.out.println("Вы открыли карту "
                                + blackjack.getLastCard(true));
                        showCards(blackjack.getPlayerHand(), blackjack.getDealerHand());
                        break;
                    case 0:
                        blackjack.action(action);
                        break;
                    default:
                        return;
                }
            }
            if (!blackjack.isEndOfRound()) {
                System.out.print("""
                        Ход Дилера
                        -------
                        """);
                System.out.println("Дилер открывает закрытую карту "
                        + blackjack.getLastCard(false));
                showCards(blackjack.getPlayerHand(), blackjack.getDealerHand());
                while (blackjack.getDealerSum() < 17) {
                    blackjack.action(1);
                    if (!blackjack.dealerBlackjack()) {
                        blackjack.dealerHasOverdone();
                    }
                    System.out.println("Дилер открыл карту "
                            + blackjack.getLastCard(false));
                    showCards(blackjack.getPlayerHand(), blackjack.getDealerHand());
                    System.out.println();
                }
                blackjack.action(0);
            }

            if (blackjack.isEndOfRound()) {
                int result = blackjack.checkForTheWin();
                if (result == 1) {
                    System.out.print("Вы выиграли раунд! ");
                } else if (result == -1) {
                    System.out.print("Дилер выиграл раунд! ");
                } else {
                    System.out.print("Ничья! ");
                }

                System.out.print("Счет " + blackjack.getCountPlayerWins()
                        + ":" + blackjack.getCountDealerWins() + " ");
                if (blackjack.getCountPlayerWins() > blackjack.getCountDealerWins()) {
                    System.out.println("в вашу пользу.");
                } else if (blackjack.getCountPlayerWins() < blackjack.getCountDealerWins()) {
                    System.out.println("в пользу дилера.");
                } else {
                    System.out.println("равный.");
                }
                System.out.println();
            }
        }
    }

    /**
     *  Interface of the game.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Game blackjack = new Game();
        System.out.println("Добро пожаловать в Блэкджек!");
        while (blackjack.getCountPlayerWins() < 1 && blackjack.getCountDealerWins() < 1) {
            playRound(blackjack);
        }
    }
}