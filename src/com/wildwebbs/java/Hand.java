package com.wildwebbs.java;

import java.util.ArrayList;

/**
 * Created by iwebb on 2/16/16.
 */
public class Hand {
    private ArrayList<Card> cards;

    // Simple initializer that takes an array to start with
    public Hand(ArrayList<Card> initialCards) {
        cards = new ArrayList<>();
        addCards(initialCards);
        organizeHand();
    }

    // Create a nice visual representation of the hand
    public String printHand() {
        String output = "";

        // Print out the hand
        for (Card card : cards) {
            output += card.print() + " ";
        }
        return output;
    }

    // Add a card to the end of the hand
    public void addCard(Card card) {
        cards.add(card);
        organizeHand();
    }

    // Method to put the cards in order
    private void organizeHand() {
        // Create a new array to store the organized hand
        int index = 0;
        ArrayList<Card> organizedCards = new ArrayList<>();

        // Go through each card rank, in order, and populate the new array
        for (int i = 0; i <= 12; i++) {
            ArrayList<Card> matches = getCardsOfValue(i);
            if (matches != null) {
                for (Card card : matches) {
                    organizedCards.add(card);
                }
            }
        }
        cards = organizedCards;
    }

    // Add multiple cards to the hand
    public void addCards(ArrayList<Card> newCards) {
        for (Card card : newCards) {
            addCard(card);
        }
    }

    // Return an array of all of the cards in the hand of value n, and remove them from the card array
    public ArrayList<Card> getCardsOfValue(int value) {
        int count = numberOf(value);

        // If there are cards, build the array; otherwise, return an array
        ArrayList<Card> matches = new ArrayList<>();
        if (count > 0) {
            // Go through and add matches to the match array and add all others to the card array
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).getValue() == value) {
                    // Remove the card, add it to the matches array, and decrement to account for removing an element
                    matches.add(cards.remove(i--));
                }
            }
            return matches;
        } else {
            return null;
        }
    }

    // Remove a card from the hand
    public boolean removeCard(Card c) {
        return cards.remove(c);
    }

    // Remove an array of cards
    public boolean removeCards(ArrayList<Card> c) {
        return cards.removeAll(c);
    }

    // Returns the number of cards with the input value
    public int numberOf(int value) {
        // Count how many cards of the input value are in the hand
        int count = 0;
        for (Card card : cards) {
            if (card.getValue() == value) count++;
        }
        return count;
    }

    // Returns the number of cards with the input suit
    public int numberOfSuit(int suit) {
        int count = 0;
        for (Card card : cards) {
            if (card.getSuit() == suit) count++;
        }
        return count;
    }

    // Returns whether or not the card exists in the hand
    public boolean contains(int value) {
        for (Card card : cards) {
            if (card.getValue() == value) return true;
        }
        return false;
    }

    // Getters and setters
    public int getCardCount() {
        return cards.size();
    }
}
