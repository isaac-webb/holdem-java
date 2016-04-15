package com.wildwebbs.java;

import java.util.ArrayList;

/**
 * Created by iwebb on 3/14/16.
 */
public class HoldEmPlayer {
    /**
     * Tracks the amount of money that a player has
     */
    private int stack;

    /**
     * Tracks the amount that a player is betting in one session of betting
     */
    private int playerBet;

    /**
     * Stores the name of the player
     */
    public String playerName;

    /**
     * Tracks whether or not a player has folded
     */
    public boolean folded;

    /**
     * Tracks whether or not this player should be treated as a computer player
     */
    public boolean isAI;

    /**
     * The hand of the player which contains all of the player's Cards
     */
    private HoldEmHand hand; // Stores the player's hand

    /**
     * Creates a new HoldEmPlayer with the standard variable values
     * @param name The name that the player should be given
     */
    public HoldEmPlayer(String name) {
        stack = 5000;
        playerBet = 0;
        playerName = name;
        folded = false;
    }

    /**
     * A wrapper for the handRank method in HoldEmHand that returns the exact same values
     * @param tableCards The community Cards from the round
     * @return
     */
    public int[] handRank(ArrayList<Card> tableCards) {
        return hand.handRank(tableCards);
    }

    /**
     * Another wrapper method that simply passes the String from the HoldEmHand toString method
     * @return
     */
    @Override
    public String toString() {
        return hand.toString();
    }

    /**
     * Executes a bet by subtracting it from the player's stack
     */
    public void collectPlayerBet() {
        stack -= playerBet;
    }

    /**
     * Returns the amount of money that the player has
     * @return The player's amount of money
     */
    public int getStack() {
        return stack;
    }

    /**
     * Adds n dollars into the player's stack
     * @param n The amount of money to add
     */
    public void addToStack(int n) {
        if (n >= 0) {
            stack += n;
        } else {
            System.out.println("Invalid winnings detected");
            System.exit(1);
        }
    }

    /**
     * Returns the player's current bet
     * @return The integer value of the bet
     */
    public int getPlayerBet() {
        return playerBet;
    }

    /**
     * Sets the player's bet
     * @param newPlayerBet The new bet that should be used
     */
    public void setPlayerBet(int newPlayerBet) {
        // Checks to make sure the bet is valid
        if (newPlayerBet < 0) {
            System.out.println("Invalid bet detected");
            System.exit(1);
        } else if (newPlayerBet > stack) {
            playerBet = stack;
        } else {
            playerBet = newPlayerBet;
        }
    }

    /**
     * Creates a new HoldEmHand internally with the Cards from the argument
     * @param initialCards The Cards that the HoldEmHand should contain
     */
    public void setHand(ArrayList<Card> initialCards) {
        if (initialCards.size() == 2) {
            hand = new HoldEmHand(initialCards);
        } else {
            System.out.println("Invalid hand detected");
        }
    }
}
