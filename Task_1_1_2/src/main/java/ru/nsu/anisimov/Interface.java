package ru.nsu.anisimov;

import java.io.Console;
import java.util.Scanner;


public class Interface {

    public static void showCards(String player, String dealer) {
        System.out.println("\tВаши карты: " + player);
        System.out.println("\tКарты дилера: " + dealer);
    }

    public static void main(String[] args) {
        Console console = System.console();

        int result;
        int action;
        Game blackjack = new Game();
        System.out.println("Добро пожаловать в Блэкджек!");
        while (blackjack.getCountPlayerWins() < 3 && blackjack.getCountDealerWins() < 3) {
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
                    action = command.nextInt();
//                    if (console == null) {
//                        if (blackjack.getPLayerSum() < 17) {
//                            action = 1;
//                        } else {
//                            action = 0;
//                        }
//                    } else {
//                        String command = System.console().readLine();
//                        if (command == null) {
//                            if (blackjack.getPLayerSum() < 17) {
//                                action = 1;
//                            } else {
//                                action = 0;
//                            }
//                        } else {
//                            action = Integer.parseInt(command);
//                        }
//                    }
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
                    System.out.println("Дилер открывает закрытую карту " + blackjack.getLastCard(false));
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
                    result = blackjack.checkForTheWin();
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
    }
}