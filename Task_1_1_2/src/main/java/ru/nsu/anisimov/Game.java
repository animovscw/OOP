package ru.nsu.anisimov;

public class Game {
    static int round = 0;
    static int countPlayerWins = 0;
    static int countDealerWins = 0;

    private boolean endOfRound;
    private boolean playerActed;

    private Deck deck;
    private Player player;
    private Player dealer;

    public void newRound() {
        deck = new Deck();
        player = new Player();
        dealer = new Player();

        player.addCard(deck.getCard());
        player.addCard(deck.getCard());
        dealer.addCard(deck.getCard());
        dealer.addCard(deck.getCard());

        player.openCard(0);
        player.openCard(1);
        dealer.openCard(0);

        playerActed = true;
        endOfRound = false;
        ++round;
    }

    public void action(int action) {
        if (action == 1) {
            if (playerActed) {
                player.addCard(deck.getCard());
                player.openLastCard();
            } else {
                dealer.addCard(deck.getCard());
                dealer.openLastCard();
            }
        } else {
            if (playerActed) {
                playerActed = false;
                dealer.openCard(1);
            } else {
                endOfRound = true;
            }
        }
    }

    public int checkForTheWin() {
        if (playerBlackjack()) {
            ++countPlayerWins;
            return 1;
        }
        if (dealerHasOverdone()) {
            ++countPlayerWins;
            return 1;
        }
        if (playerHasOverdone()) {
            ++countDealerWins;
            return -1;
        }
        if (dealerBlackjack()) {
            ++countDealerWins;
            return -1;
        }
        return whoHasBiggerSum();
    }

    public boolean playerBlackjack() {
        if (player.getSum() == 21) {
            playerActed = false;
            endOfRound = true;
            return true;
        }
        return false;
    }


    public boolean dealerBlackjack() {
        if (dealer.getSum() == 21) {
            endOfRound = true;
            return true;
        }
        return false;
    }

    public boolean playerHasOverdone() {
        if (player.getSum() > 21) {
            playerActed = false;
            endOfRound = true;
            return true;
        }
        return false;
    }

    public boolean dealerHasOverdone() {
        if (dealer.getSum() > 21) {
            endOfRound = true;
            return true;
        }
        return false;
    }

    public int whoHasBiggerSum() {
        if (player.getSum() > dealer.getSum()) {
            ++countPlayerWins;
            return 1;
        } else if (player.getSum() < dealer.getSum()) {
            ++countDealerWins;
            return -1;
        }
        return 0;
    }

    public int getRound() {
        return round;
    }

    public String getPlayerHand() {
        return player.showCardsInHand();
    }

    public String getDealerHand() {
        return dealer.showCardsInHand();
    }

    public boolean isEndOfRound() {
        return endOfRound;
    }

    public boolean isPlayerActed() {
        return playerActed;
    }

    public int getCountPlayerWins() {
        return countPlayerWins;
    }

    public int getCountDealerWins() {
        return countDealerWins;
    }

    public int getDealerSum() {
        return dealer.getSum();
    }

    public String getLastCard(boolean playerActed) {
        if (playerActed) {
            return player.showLastCard();
        } else {
            return dealer.showLastCard();
        }
    }
}
