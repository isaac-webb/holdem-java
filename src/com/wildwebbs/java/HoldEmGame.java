package com.wildwebbs.java;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by iwebb on 3/10/16.
 */
public class HoldEmGame {
    // Create a console that can be used by any method
    private static Console console;

    // Keep track of players
    private static ArrayList<HoldEmPlayer> holdEmPlayers;

    // Variables to track the state of a round
    private static int pot;
    private static int dealer;
    private static int currentBet;
    private static HoldEmPlayer lastRaise;
    private static ArrayList<Card> tableCards;

    public static void main(String[] args) {
        // Bomb out if the inferior console is nonexistent
        if ((console = System.console()) == null) {
            System.out.println("Please execute this Java program in a terminal window");
            System.exit(1);
        }

        // Print the welcome message and get the number of players
        System.out.print("Welcome to Texas Hold 'Em! How many players will there be today (2-4)? ");
        int players = 0;
        while (players == 0) {
            try {
                players = Integer.parseInt(console.readLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number of players: ");
            }
            if (players > 4 || players < 2) {
                players = 0;
                System.out.print("Please enter a valid number of players: ");
            }
        }

        // Initialize the player array and get names
        holdEmPlayers = new ArrayList<>(players);
        for (int i = 0; i < players; i++) {
            System.out.print("Please enter a name for player " + (i + 1) + ": ");
            holdEmPlayers.add(new HoldEmPlayer(console.readLine()));
        }
        clearScreen();

        // Make the first dealer player 1
        dealer = 0;

        // This is the loop that runs all of the game
        while (true) {
            // Create variables to store community cards and the pot
            tableCards = new ArrayList<>();
            pot = 0;
            currentBet = 0;

            // Deal cards to all players
            Deck deck = new Deck();
            for (int i = 0; i < holdEmPlayers.size(); i++) {
                holdEmPlayers.get((i + dealer) % holdEmPlayers.size()).folded = false;
                holdEmPlayers.get((i + dealer) % holdEmPlayers.size()).setHand(deck.drawCards(2));
            }

            // Collect all bets
            collectBets();

            // Check to see if only one player is left
            if (playersLeft() == 1) {
                evaluateRound();
                continue;
            }

            // Draw the flop
            clearScreen();
            tableCards.addAll(deck.drawCards(3));

            // Collect all bets
            collectBets();

            // Check to see if only one player is left
            if (playersLeft() == 1) {
                evaluateRound();
                continue;
            }

            // Draw the turn
            clearScreen();
            tableCards.add(deck.drawCard());

            // Collect all bets
            collectBets();

            // Check to see if only one player is left
            if (playersLeft() == 1) {
                evaluateRound();
                continue;
            }

            // Draw the river
            clearScreen();
            tableCards.add(deck.drawCard());

            // Collect all bets
            collectBets();

            evaluateRound();
        }
    }

    // Evaluates hands and determines the winner of the round, also checks for end of game and removes players who have no money
    private static void evaluateRound() {
        // Print out everybody's hand
        System.out.println("Table Cards:");
        System.out.println(printTableCards() + "\n");
        for (HoldEmPlayer p : holdEmPlayers) {
            System.out.println(p.playerName + " " + (p.folded ? "Folded":""));
            System.out.println(p.printHand());
        }

        // Get all of the hands and compare them
        ArrayList<int[]> hands = new ArrayList<>();
        for (int p = 0; p < holdEmPlayers.size(); p++) {
            hands.add(holdEmPlayers.get(p).handRank(tableCards));
        }

        // Create variables to store winners and the highest hand
        ArrayList<Integer> winners = new ArrayList<>();
        int[] max = {-1, -1, -1, -1};

        // Decide which player(s) won
        winnerLoop:
        for (int p = 0; p < hands.size(); p++) {
            if (!holdEmPlayers.get(p).folded) {
                for (int i = 0; i < hands.get(p).length; i++) {
                    // If the player's hand is better, remove all previous winners, set the max to their hand, and make them a winner
                    if (hands.get(p)[i] > max[i]) {
                        winners.clear();
                        winners.add(p);
                        max = hands.get(p);
                        continue winnerLoop;
                    } else if (hands.get(p)[i] < max[i]) continue winnerLoop;
                    // If the player's hand is worse, move on to the next one
                }
                // If the hands are exactly equal, then add the player as a winner
                winners.add(p);
            }
        }

        // Award the player(s) with the best hand(s) the pot
        for (int p = 0; p < winners.size(); p++) {
            System.out.println(holdEmPlayers.get(winners.get(p)).playerName + " won " + (pot/(winners.size() - p)) + "!");
            holdEmPlayers.get(winners.get(p)).addToStack(pot/(winners.size() - p));
            pot -= pot/(winners.size() - p);
        }

        // Check to see if anyone is out of money, and if they are, remove them
        for (int p = 0; p < holdEmPlayers.size(); p++) {
            if (holdEmPlayers.get(p).getStack() <= 0) {
                System.out.println(holdEmPlayers.get(p).playerName + " is out!");
                holdEmPlayers.remove(p);

                // Make sure the dealer will be properly assigned to the next player
                if (p <= dealer) dealer--;
                p--; // Decrement to account for the smaller array
            }
        }

        // If there is one player left, then tell 'em!
        if (holdEmPlayers.size() == 1) {
            System.out.println(holdEmPlayers.get(0).playerName + " won with $" + holdEmPlayers.get(0).getStack() + "!");
            System.exit(0);
        }

        // Otherwise, prompt to continue
        System.out.print("Press ENTER to continue: ");
        console.readLine();

        // Make the dealer the next player
        dealer = (dealer + 1) % holdEmPlayers.size();

        clearScreen();
    }

    // Prints out a bunch of new lines to "clear" the screen
    private static void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    // Loops through players and determines what to ask based on the game situation
    private static void collectBets() {
        int p = 1;
        currentBet = 0;
        lastRaise = holdEmPlayers.get((p + dealer) % holdEmPlayers.size());
        boolean checkOccurred = false;
        while (true) {
            // Exit this method if all other players have folded
            if (playersLeft() == 1) break;

            // Get the player who should be going
            int trueIndex = (p + dealer) % holdEmPlayers.size();
            HoldEmPlayer player = holdEmPlayers.get(trueIndex);

            // Check to see if a raise has been dealt with by everyone
            if (lastRaise != player || !checkOccurred) {
                // Make sure the player is able to go
                if (!player.folded && player.getStack() > 0) {
                    // Present the appropriate options for the players
                    if (currentBet > 0) {
                        askPlayerCall(player);
                        pot += player.getPlayerBet();
                        player.collectPlayerBet();
                    } else {
                        askPlayerCheck(player);
                        pot += player.getPlayerBet();
                        player.collectPlayerBet();
                        checkOccurred = true;
                    }
                }
            } else break;
            p++;
            clearScreen();
        }
        for (HoldEmPlayer player : holdEmPlayers) {
            player.setPlayerBet(0);
        }
    }

    // Asks player to fold, check, or raise, and returns the amount that they bet
    private static void askPlayerCheck(HoldEmPlayer player) {
        // Print all necessary information for the user
        System.out.println("Table Cards:");
        System.out.println(printTableCards() + "\n");
        System.out.println(player.playerName);
        System.out.println("Stack: " + player.getStack() + ", Pot: " + pot);
        System.out.println(player.printHand());
        System.out.print("Would you like to fold, check, or raise? ");

        // Get the operation that the user would like to perform
        String operation;
        while (true) {
            operation = console.readLine();
            if (operation.equalsIgnoreCase("fold")) {
                player.folded = true;
                break;
            } else if (operation.equalsIgnoreCase("check")) {
                break;
            } else if (operation.equalsIgnoreCase("raise")) {
                if (player.getStack() <= currentBet) {
                    System.out.print("You don't have enough to raise! Enter another operation: ");
                    continue;
                }
                raiseWithPlayer(player);
                break;
            } else {
                System.out.print("Please enter a valid operation: ");
            }
        }
    }

    // Go through the raise process with the player passed in
    private static void raiseWithPlayer(HoldEmPlayer player) {
        // Adjust the pot and stack so everything will update correctly
        pot -= player.getPlayerBet();
        player.addToStack(player.getPlayerBet());

        System.out.print("What would you like to raise to? ");
        int raiseTo = 0;

        // Get a bet and make sure it is both larger than the current bet and less than or equal to the player's money
        while (raiseTo == 0) {
            try {
                raiseTo = Integer.parseInt(console.readLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid bet: ");
                raiseTo = 0;
            }
            if (raiseTo <= currentBet || raiseTo > player.getStack()) {
                System.out.print("Please enter a valid bet: ");
                raiseTo = 0;
            }
        }

        // Update the player's bet and the current bet
        currentBet = raiseTo;
        lastRaise = player;
        player.setPlayerBet(raiseTo);
    }

    // Asks player to fold, call, or raise, and returns the amount that they bet
    private static void askPlayerCall(HoldEmPlayer player) {
        // Print all necessary information for the user
        System.out.println("Table Cards:");
        System.out.println(printTableCards() + "\n");
        System.out.println(player.playerName);
        System.out.println("Stack: " + player.getStack() + ", Your Bet: " + player.getPlayerBet() + ", Current Bet: " + currentBet + ", Pot: " + pot);
        System.out.println(player.printHand());
        System.out.print("Would you like to fold, call, or raise? ");

        // Get the operation that the user would like to perform
        String operation;
        while (true) {
            operation = console.readLine();
            if (operation.equalsIgnoreCase("fold")) {
                player.folded = true;
                break;
            } else if (operation.equalsIgnoreCase("call")) {
                // Adjust the pot and the player's stack to ensure correct updating
                player.addToStack(player.getPlayerBet());
                pot -= player.getPlayerBet();

                // Make sure the player isn't betting more than they have
                if (player.getStack() < currentBet) player.setPlayerBet(player.getStack());
                else player.setPlayerBet(currentBet);
                break;
            } else if (operation.equalsIgnoreCase("raise")) {
                if (player.getStack() <= currentBet) {
                    System.out.print("You don't have enough to raise! Enter another operation: ");
                    continue;
                }
                raiseWithPlayer(player);
                break;
            } else {
                System.out.print("Please enter a valid operation: ");
            }
        }
    }

    // Simple method that prints the community cards nicely
    private static String printTableCards() {
        if (tableCards.size() > 0) {
            String str = "";
            for (Card c : tableCards) {
                str += c.print() + " ";
            }
            return str;
        } else {
            return "None!";
        }
    }

    private static int playersLeft() {
        int cnt = 0;
        for (HoldEmPlayer p : holdEmPlayers) {
            if (!p.folded) cnt++;
        }
        return cnt;
    }
}
