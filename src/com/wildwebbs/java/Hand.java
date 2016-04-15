package com.wildwebbs.java;

import java.util.ArrayList;

/**
 * Hand is a class that represents a typical hand of cards. It differs from a Deck in that it can contain pattern matching and is able to be printed.
 *
 * @author Isaac Webb
 * @version 1.1
 * @since 2016-04-12
 */
public class Hand {
    /**
     * An ArrayList that stores the Cards contained in the Hand
     */
    private ArrayList<Card> cards;

    /**
     * Creates a new Hand with the Cards in the given ArrayList
     * @param initialCards The Cards that the Hand should contain
     */
    public Hand(ArrayList<Card> initialCards) {
        cards = new ArrayList<>();
        addCards(initialCards);
        organizeHand();
    }

    /**
     * Returns a nice visual representation of the Hand by concatenating all of the Cards' String descriptions
     * @return A String containing a visual of the Cards in the Hand
     */
    @Override
    public String toString() {
        String output = "";

        // Print out the hand
        for (Card card : cards) {
            output += card + " ";
        }
        return output;
    }

    /**
     * Adds a Card into the Hand
     * @param card The Card to be added into the Hand
     */
    public void addCard(Card card) {
        cards.add(card);
        organizeHand();
    }

    /**
     * Puts the Hand in order based upon value
     */
    private void organizeHand() {
        // Create a new array to store the organized hand
        ArrayList<Card> organizedCards = new ArrayList<>();

        // Go through each card rank, in order, and populate the new array
        for (int i = 0; i <= 12; i++) {
            ArrayList<Card> matches = getCardsOfValue(i);
            if (matches != null) {
                organizedCards.addAll(matches);
            }
        }
        cards = organizedCards;
    }

    /**
     * Adds an ArrayList of Cards into the Hand
     * @param newCards The ArrayList containing the Cards that will be added into the Hand
     */
    public void addCards(ArrayList<Card> newCards) {
        for (Card card : newCards) {
            addCard(card);
        }
    }

    /**
     * Returns all of the Cards in the Hand with the given value and removes them from the ArrayList
     * @param value The value of the Cards that should be removed and returned
     * @return An ArrayList containing all of the Cards with the given value
     */
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

    /**
     * Returns the number of Cards in the Hand with the given value
     * @param value The value of the Cards that should be counted
     * @return The number of Cards in the Hand with the given value
     */
    public int numberOf(int value) {
        int count = 0;
        for (Card card : cards) {
            if (card.getValue() == value) count++;
        }
        return count;
    }

    /**
     * Returns the number of Cards in the Hand with the given suit
     * @param suit The suit of the Cards that should be counted
     * @return The number of Cards in the Hand with the given suit
     */
    public int numberOfSuit(int suit) {
        int count = 0;
        for (Card card : cards) {
            if (card.getSuit() == suit) count++;
        }
        return count;
    }

    /**
     * Checks to see whether or not a single Card with the given value exists in the Hand
     * @param value The value that should be checked
     * @return Whether or not the Hand contains a Card with a value of value
     */
    public boolean contains(int value) {
        for (Card card : cards) {
            if (card.getValue() == value) return true;
        }
        return false;
    }

    /**
     * Returns the number of Cards that are contained in the ArrayList of the Hand
     * @return The size of the ArrayList/number of Cards in the Hand
     */
    public int getCardCount() {
        return cards.size();
    }
}
