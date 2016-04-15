package com.wildwebbs.java;

/**
 * Playing Card is a class that represents a playing card from a standard 52-card deck. It provides some useful text output methods.
 *
 * Values 0-12 represent A-K, in order.
 * Suits 0, 1, 2, 3 represent Hearts, Diamonds, Clubs, and Spades, respectively.
 *
 * @author Isaac Webb
 * @version 1.1
 * @since 2016-04-12
 */
public class PlayingCard extends Card {

    /**
     * Creates a playing card with the given value and suit. It also checks to make sure the values are valid for playing cards.
     * @param v The value of the playing card to be created (0-12)
     * @param s The suit of the playing card to be created (0-3)
     * @throws InvalidPlayingCardException
     */
    public PlayingCard(int v, int s) throws InvalidPlayingCardException {
        super(v, s);
        if (!(v >= 0 && v <= 12 && s >= 0 && s <= 3)) {
            throw new InvalidPlayingCardException(v, s);
        }
    }

    /**
     * Returns a nice String representation of the playing card using unicode symbols and pipes
     * @return A string "card" in the format | &lt;suit symbol&gt; &lt;value&gt; |
     */
    @Override
    public String toString() {
        String description = "";

        // Put the unicode symbol for the suit into the description
        switch (getSuit()) {
            case 0:
                description += "\u2665 ";
                break;
            case 1:
                description += "\u25C6 ";
                break;
            case 2:
                description += "\u2663 ";
                break;
            case 3:
                description += "\u2660 ";
                break;
            default:
                description += "Whoops ";
                break;
        }

        // Put the symbol for the value into the description
        if (getValue() == 0) {
            description += "A";
        } else if (getValue() <= 9) {
            description += getValue() + 1;
        } else if (getValue() == 10) {
            description += "J";
        } else if (getValue() == 11) {
            description += "Q";
        } else if (getValue() == 12) {
            description += "K";
        } else {
            description += "Whoops";
        }

        // Put pipes around the description to make it look like a card
        return "| " + description + " |";
    }
}
