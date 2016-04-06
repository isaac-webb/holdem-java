package com.wildwebbs.java;

import java.util.ArrayList;

/**
 * Created by iwebb on 3/14/16.
 */
public class HoldEmPlayer {
    // Variables to track betting
    private int stack; // Stores how much money the player has
    private int playerBet; // Stores how much money the player is going to bet on their hand
    public String playerName; // Stores the name of the player
    public boolean folded;
    public boolean isAI;
    private HoldEmHand hand; // Stores the player's hand

    // Standard initializer that simply takes the player's name
    public HoldEmPlayer(String name) {
        stack = 5000;
        playerBet = 0;
        playerName = name;
        folded = false;
    }

    // Return's the hand's rank information
    public int[] handRank(ArrayList<Card> tableCards) {
        return hand.handRank(tableCards);
    }

    // Rreturn the hand's visual representation
    public String printHand() {
        return hand.printHand();
    }

    // At the end of a round, this method should be executed to
    public void collectPlayerBet() {
        stack -= playerBet;
    }

    // Getters and setters
    public int getStack() {
        return stack;
    }

    public void addToStack(int n) {
        if (n >= 0) {
            stack += n;
        } else {
            System.out.println("Invalid winnings detected");
            System.exit(1);
        }
    }

    public int getPlayerBet() {
        return playerBet;
    }

    public void setPlayerBet(int newPlayerBet) {
        // Checks to make sure the bet is valid
        if (newPlayerBet < 0) {
            System.out.println("Invalid bet detected");
            return;
        } else if (newPlayerBet > stack) {
            playerBet = stack;
        } else {
            playerBet = newPlayerBet;
        }
    }

    public void setHand(ArrayList<Card> initialCards) {
        if (initialCards.size() == 2) {
            hand = new HoldEmHand(initialCards);
        } else {
            System.out.println("Invalid hand detected");
        }
    }
}
