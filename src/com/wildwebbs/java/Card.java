package com.wildwebbs.java;

/**
 * Created by iwebb on 2/2/16.
 */
public class Card {
    private int value; // 0-12 -> A-K; 0-14 0 - Wild, +4
    private int suit; // 0-3 -> Hearts, Diamonds, Clubs, Spades; 0-3 R, Y, G, B

    public Card(int v, int s) {
        value = v;
        suit = s;
    }

    public String print() {
        return "Value: " + value + ", Suit: " + suit;
    }

    // Getters and setters
    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }
}
