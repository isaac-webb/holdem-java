package com.wildwebbs.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by iwebb on 2/2/16.
 */

public class Deck {
    private ArrayList<Card> cardDeck;

    // Creates a new deck of playing cards
    public Deck() {
        // Create a deck that is easier to pick from
        cardDeck = new ArrayList<>(52);

        // Create every single kind of card (A-K and hearts, diamonds, clubs, spades)
        for (int v = 0; v <= 12; v++) {
            for (int s = 0; s <= 3; s++) {
                cardDeck.add(new PlayingCard(v, s));
            }
        }

        // Shuffle the deck
        shuffleDeck();
    }

    // Simple initializer that creates an empty array with size of the input size
    private Deck(int size) {
        cardDeck = new ArrayList<>(size);
    }

    // Shuffle the deck
    private void shuffleDeck() {
        Deck deck = new Deck(1);
        deck.mergeDeck(this);
        mergeDeck(deck);
    }

    // Draw a card, assuming the deck is not empty. If it is, return null
    public Card drawCard() {
        // Make sure that the deck is not empty
        if (cardDeck.size() > 0) {
            // Create a random number generator
            Random indexGen = new Random();

            // Generate a random index and return the object
            return cardDeck.remove(indexGen.nextInt(cardDeck.size()));
        } else {
            return null;
        }
    }

    // Draw n cards from the deck and return null if there were not enough cards left
    public ArrayList<Card> drawCards(int n) {
        // Create an array of drawn cards
        ArrayList<Card> draws = new ArrayList<>();

        // Loop through the array and draw a card for each position, returning null if the deck doesn't have enough left
        for (int i = 0; i < n; i++) {
            Card pick = drawCard();
            if (pick == null) return null;
            draws.add(pick);
        }
        return draws;
    }

    // Merge two decks (shuffles as well)
    private void mergeDeck(Deck d) {
        Card card;
        while ((card = d.drawCard()) != null) cardDeck.add(card);
    }

    // Getters and setters
    public int getCardDeckCount() {
        return cardDeck.size();
    }
}
