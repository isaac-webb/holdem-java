package com.wildwebbs.java;

import java.util.Scanner;

/**
 * Created by iwebb on 2/2/16.
 */
public class GoFishGame {
    public static void main(String[] args) {
        // Generate a new deck of playing cards
        Deck deck = new Deck();

        // Generate random hands
        GoFishHand player1 = new GoFishHand(deck.drawCards(7));
        GoFishHand player2 = new GoFishHand(deck.drawCards(7));

        // Start with player 1, ask for rank, make sure it is in hand
        while (player1.getBooks() + player2.getBooks() != 13) {
            System.out.println("Player 1\n----------------------");
            playTurn(player1, player2, deck);
            if (player1.getBooks() + player2.getBooks() == 13) {
                System.out.println("Game Over!");
                break;
            }
            System.out.println("Player 2\n----------------------");
            playTurn(player2, player1, deck);
        }

        if (player1.getBooks() > player2.getBooks()) {
            System.out.println("Player 1 Won!");
        } else {
            System.out.println("Player 2 Won!");
        }
    }

    public static void playTurn(GoFishHand player1, GoFishHand player2, Deck deck) {
        // Check to make sure the player has cards
        if (player1.getCardCount() == 0) {
            if (deck.getCardDeckCount() > 0) {
                player1.addCard(deck.drawCard());
            } else {
                System.out.println("GoFishGame Over!");
                return;
            }
        }

        // Variable to track whether the turn has ended
        boolean hadCatch = true;

        // Create an input
        Scanner input = new Scanner(System.in);

        // Keep going until the turn ends
        while (hadCatch) {
            // Put the code in its loop state for the first run
            int value = -1;
            while (value == -1) {
                // Show the hand and prompt for a value
                System.out.println(player1.printHand() + " Books: " + player1.getBooks() + "  Deck: " + deck.getCardDeckCount());
                System.out.println("Enter the rank of the card you would like to ask for (must be in your hand):");

                // Map the input string to a rank
                value = mapStringToValue(input.nextLine());

                // Make sure that the card actually is in the player's hand
                if (player1.contains(value)) {
                    // If the other player has any of those cards, take them
                    if (player2.contains(value)) {
                        // Add cards of the specified rank to the current player's hand
                        player1.addCards(player2.getCardsOfValue(value));

                        // Check to decide whether or not
                        if (player1.getCardCount() == 0) {
                            if (deck.getCardDeckCount() > 0) {
                                player1.addCard(deck.drawCard());
                            } else {
                                System.out.println("GoFishGame Over!");
                                return;
                            }
                        }
                        System.out.println("Keep Going!");
                    } else {
                        System.out.println("Go Fish!");
                        Card newCard = deck.drawCard();
                        if (newCard == null) {
                            System.out.println("The deck is empty!");
                        } else {
                            System.out.println("You drew: " + newCard.print());
                            player1.addCard(newCard);
                        }

                        // Pause so the user has time to read
                        System.out.println("Press ENTER to continue");
                        input.nextLine();

                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        hadCatch = false;
                    }
                } else {
                    // Reset to repeat the input
                    value = -1;
                }
            }
        }
    }

    // Method to map inputs to rank values
    public static int mapStringToValue(String value) {
        if (value.equalsIgnoreCase("a")) {
            return 0;
        } else if (value.equalsIgnoreCase("j")) {
            return 10;
        } else if (value.equalsIgnoreCase("q")) {
            return 11;
        } else if (value.equalsIgnoreCase("k")) {
            return 12;
        } else if (value.equalsIgnoreCase("2")) {
            return 1;
        } else if (value.equalsIgnoreCase("3")) {
            return 2;
        } else if (value.equalsIgnoreCase("4")) {
            return 3;
        } else if (value.equalsIgnoreCase("5")) {
            return 4;
        } else if (value.equalsIgnoreCase("6")) {
            return 5;
        } else if (value.equalsIgnoreCase("7")) {
            return 6;
        } else if (value.equalsIgnoreCase("8")) {
            return 7;
        } else if (value.equalsIgnoreCase("9")) {
            return 8;
        } else if (value.equalsIgnoreCase("10")) {
            return 9;
        } else {
            return -1;
        }
    }
}