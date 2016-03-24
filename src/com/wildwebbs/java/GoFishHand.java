package com.wildwebbs.java;

import java.util.ArrayList;

/**
 * Created by iwebb on 2/2/16.
 */
public class GoFishHand extends Hand {
    private int books;

    public GoFishHand(ArrayList<Card> initialCards) {
        super(initialCards);
    }

    // Override to perform the "book check"
    @Override
    public void addCard(Card card) {
        super.addCard(card);
        checkForBooks();
    }

    // Removes any groups of four
    public void checkForBooks() {
        for (int i = 0; i <= 12; i++) {
            if (numberOf(i) == 4) {
                books++;
                getCardsOfValue(i);
            }
        }
    }

    // Getters and setters
    public int getBooks() {
        return books;
    }
}
