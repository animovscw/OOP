package ru.nsu.anisimov;

/**
 * The Game class represents the core logic of a game.
 */
public class Game {
    /**
     * The current round number.
     */
    static int round = 0;

    /**
     * The number of rounds won by player.
     */
    static int countPlayerWins = 0;

    /**
     * The number of rounds won by dealer.
     */
    static int countDealerWins = 0;

    /**
     * Indicates whether the round has ended.
     */
    private boolean endOfRound;

    /**
     * Indicates whether the player is still acting.
     */
    private boolean playerActed;

    /**
     * The deck of cards used in the game.
     */
    private Deck deck;

    /**
     * The player in the game.
     */
    Player player;

    /**
     * The dealer(AI player) in the game.
     */
    Player dealer;

    /**
     * Starts a new round in the game. Initializes the deck and gives both players two cards.
     */
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

    /**
     * Processes the player's or dealer's action based on the given input command.
     *
     * @param action the action to perform: 1 - draw the card, 0 - stop.
     */
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

    /**
     * Checks the win conditions and determines the winner of the round.
     *
     * @return 1 if the player wins, -1 if the dealer wins, or 0 if it's a draw.
     */
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
        }
        if (dealerBlackjack()) {
            ++countDealerWins;
            return -1;
        }
        return whoHasBiggerSum();
    }

    /**
     * Checks if the player has achieved Blackjack ( sum = 21).
     *
     * @return true if the player has Blackjack, false otherwise.
     */
    public boolean playerBlackjack() {
        if (player.getSum() == 21) {
            playerActed = false;
            endOfRound = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if the dealer has achieved Blackjack ( sum = 21).
     *
     * @return true if the dealer has Blackjack, false otherwise.
     */
    public boolean dealerBlackjack() {
        if (dealer.getSum() == 21) {
            endOfRound = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if the player has more than 21 points.
     *
     * @return true if the player has overdone, false otherwise.
     */
    public boolean playerHasOverdone() {
        if (player.getSum() > 21) {
            playerActed = false;
            endOfRound = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if the dealer has more than 21 points.
     *
     * @return true if the dealer has overdone, false otherwise.
     */
    public boolean dealerHasOverdone() {
        if (dealer.getSum() > 21) {
            endOfRound = true;
            return true;
        }
        return false;
    }

    /**
     * Compares the sum between two players.
     *
     * @return 1 if the player wins, -1 if the dealer wins.
     */
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

    /**
     * Returns the current round number.
     *
     * @return the current round number.
     */
    public int getRound() {
        return round;
    }

    /**
     * Returns the player's current hand of cards.
     *
     * @return a string representation of the player's hand.
     */
    public String getPlayerHand() {
        return player.showCardsInHand();
    }

    /**
     * Returns the dealer's current hand of cards.
     *
     * @return a string representation of the dealer's hand.
     */
    public String getDealerHand() {
        return dealer.showCardsInHand();
    }

    /**
     * Checks if the current number has ended.
     *
     * @return true if round has ended, false otherwise.
     */
    public boolean isEndOfRound() {
        return endOfRound;
    }

    /**
     * Checks if the player is still taking cards.
     *
     * @return true if player is still acting, false otherwise.
     */
    public boolean isPlayerActed() {
        return playerActed;
    }

    /**
     * Returns the number of rounds won by the player.
     *
     * @return the number of rounds won by the player.
     */
    public int getCountPlayerWins() {
        return countPlayerWins;
    }

    /**
     * Returns the number of rounds won by the dealer.
     *
     * @return the number of rounds won by the dealer.
     */
    public int getCountDealerWins() {
        return countDealerWins;
    }

    /**
     * Returns the current sum of the dealer's cards.
     *
     * @return the sum of the dealer's hand.
     */
    public int getDealerSum() {
        return dealer.getSum();
    }

    /**
     * Returns the last card that was drawn by either the player or the dealer.
     *
     * @param playerActed true if last card was drawn by player, false for the dealer.
     * @return a string representation of the last card.
     */
    public String getLastCard(boolean playerActed) {
        if (playerActed) {
            return player.showLastCard();
        } else {
            return dealer.showLastCard();
        }
    }
}
