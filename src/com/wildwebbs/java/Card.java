package com.wildwebbs.java;

/**
 * Card is class that represents some sort of generic card. This class is designed so that an instance of it cannot be modified after it is created.
 *
 * @author Isaac Webb
 * @version 1.1
 * @since 2016-04-12
 */
public class Card {
    /**
     * An integer that represents the value of the card.
     */
    private int value;
    /**
     * An integer that represents the suit/color/secondary characteristic of the card.
     */
    private int suit;

    /**
     * Creates a new Card with the given value and suit.
     * @param v The value of the Card to be created
     * @param s The suit of the Card to be created
     */
    public Card(int v, int s) {
        value = v;
        suit = s;
    }

    /**
     * Returns a summary of the Card's properties.
     * @return A string with the value and suit written out
     */
    @Override
    public String toString() {
        return "Value: " + value + ", Suit: " + suit;
    }

    /**
     * Returns the value of the card.
     * @return The integer value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the suit of the card.
     * @return The integer suit of the card
     */
    public int getSuit() {
        return suit;
    }
}
