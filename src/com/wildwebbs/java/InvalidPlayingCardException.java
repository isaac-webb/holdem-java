package com.wildwebbs.java;

/**
 * InvalidPlayingCardException is the exception thrown when a playing card has an invalid value or suit
 *
 * @author Isaac Webb
 * @version 1.1
 * @since 2016-04-12
 */
public class InvalidPlayingCardException extends Exception {
    /**
     * Creates an InvalidPlayingCardException
     * @param v The value of the card attempting to be created
     * @param s The suit of the card attempting to be created
     */
    public InvalidPlayingCardException(int v, int s) {
        super("Value: " + v + ", Suit: " + s);
    }
}
