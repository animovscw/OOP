package ru.nsu.anisimov;

import java.util.Scanner;

public class Interface {
    static int round = 1; //store the current round
    static int countPlayerWins = 0; //amount of rounds won by player
    static int countDealerWins = 0; //amount of rounds won by dealer

    static void playerWin() {
        ++countPlayerWins;
        System.out.print("Вы выиграли раунд! ");
        System.out.printf("Счёт %d:%d ",
                countPlayerWins, countDealerWins);
        if (countPlayerWins > countDealerWins) {
            System.out.println("в вашу пользу.");
        } else if (countPlayerWins < countDealerWins) {
            System.out.println("в пользу дилера.");
        }
        ++round;
    }

    static void playerLoose() {
        ++countDealerWins;
        System.out.print("Вы проиграли раунд! ");
        System.out.printf("Счёт %d:%d ",
                countPlayerWins, countDealerWins);
        if (countPlayerWins > countDealerWins) {
            System.out.println("в вашу пользу.");
        } else if (countPlayerWins < countDealerWins) {
            System.out.println("в пользу дилера.");
        }
        ++round;
    }

    static void checkForTheWin(Player player, Player dealer) {
        if (dealer.sum > 21) {
            playerWin();
        } else if (player.sum <= 21) {
            if (player.sum > dealer.sum) {
                playerWin();
            }
            if (player.sum < dealer.sum) {
                playerLoose();
            }
            if (player.sum == dealer.sum) {
                System.out.print("Ничья! ");
                System.out.printf("Счёт %d:%d ",
                        countPlayerWins, countDealerWins);
                if (countPlayerWins > countDealerWins) {
                    System.out.println("в вашу пользу.");
                } else if (countPlayerWins < countDealerWins) {
                    System.out.println("в пользу дилера.");
                }
            }
        }
    }

    static void showCards(Player player, Player dealer) {
        System.out.print("Ваши карты: [");
        for (int index = 0; index < player.hand.size(); ++index) {
            if (index == player.hand.size() - 1) {
                System.out.printf("%s %s (%d)]",
                        player.hand.get(index).suit,
                        player.hand.get(index).rank,
                        player.hand.get(index).value);

            } else {
                System.out.printf("%s %s (%d), ",
                        player.hand.get(index).suit,
                        player.hand.get(index).rank,
                        player.hand.get(index).value);
            }
        }
        System.out.printf(" -> %d\n", player.sum);
        System.out.print("Карты дилера: [");
        for (int index = 0; index < dealer.hand.size(); ++index) {
            if (dealer.hand.get(index).isCardHidden) {
                System.out.print("<закрытая карта>]");
                break;
            }
            if (index == dealer.hand.size() - 1) {
                System.out.printf("%s %s (%d)]",
                        dealer.hand.get(index).suit,
                        dealer.hand.get(index).rank,
                        dealer.hand.get(index).value);

            } else {
                System.out.printf("%s %s (%d), ",
                        dealer.hand.get(index).suit,
                        dealer.hand.get(index).rank,
                        dealer.hand.get(index).value);
            }
        }
        if (!dealer.isCardHidden) {
            System.out.printf(" -> %d\n", dealer.sum);
        } else {
            System.out.println();
        }
    }

    static boolean Blackjack(Player player) {
        return player.hand.size() == 2 && player.sum == 21;
    }

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Блэкджек!");
        Scanner scan = new Scanner(System.in);
        while (countPlayerWins < 3 && countDealerWins < 3) {
            Deck deck = new Deck();
            Player player = new Player();
            Player dealer = new Player();
            int actionCommand;
            player.getCard(deck);
            player.getCard(deck);

            dealer.getCard(deck);
            dealer.getCard(deck);

            dealer.isCardHidden = true;
            dealer.hand.getLast().isCardHidden = true;
            System.out.printf("Раунд %d\nДилер раздал карты\n", round);
            showCards(player, dealer);
            if (Blackjack(player)) {
                playerWin();
                continue;
            }
            System.out.print("Ваш ход\n-------\n");
            while (true) {
                if (player.sum > 21) {
                    playerLoose();
                    break;
                }
                System.out.print("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться\n");
                actionCommand = scan.nextInt();
                if (actionCommand == 1) {
                    player.getCard(deck);
                    System.out.printf("Вы открыли карту %s %s (%d)\n",
                            player.hand.getLast().rank,
                            player.hand.getLast().suit,
                            player.hand.getLast().value);
                    showCards(player, dealer);
                } else if (actionCommand == 0) {
                    break;
                }
//                else {
//                    do {
//                        System.out.print("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться\n");
//                        actionCommand = scan.nextInt();
//                    } while (actionCommand != 1 && actionCommand != 0);
//                }
            }
            if (player.sum > 21) {
                continue;
            }
            System.out.println("Ход дилера\n-------");
            dealer.hand.getLast().isCardHidden = false;
            dealer.isCardHidden = false;
            System.out.printf("Дилер открывает закрытую карту %s %s (%d)\n",
                    dealer.hand.getLast().rank,
                    dealer.hand.getLast().suit,
                    dealer.hand.getLast().value);

            showCards(player, dealer);
            if (Blackjack(dealer)) {
                playerLoose();
                continue;
            }
            while (dealer.sum <= 17) {
                dealer.getCard(deck);
                System.out.printf("Дилер открыл карту %s %s (%d)\n",
                        dealer.hand.getLast().rank,
                        dealer.hand.getLast().suit,
                        dealer.hand.getLast().value);
                showCards(player, dealer);
            }
            checkForTheWin(player, dealer);
        }
    }
}