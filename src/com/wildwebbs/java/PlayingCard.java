package com.wildwebbs.java;

/**
 * Created by iwebb on 2/12/16.
 */
public class PlayingCard extends Card {

    public PlayingCard(int v, int s) {
        super(v, s);
    }

    // Method to return a string description
    @Override
    public String print() {
        String description = "";
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

        return "| " + description + " |";
    }
}
