package com.wildwebbs.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * Deck is a class that represents a generic collection of cards with common deck behaviors.
 *
 * @author Isaac Webb
 * @version 1.1
 * @since 2016-04-12
 */
public class Deck {
    /**
     * An ArrayList of Cards that stores the cards contained in the Deck
     */
    private ArrayList<Card> cardDeck;

    /**
     * Creates a new Deck of PlayingCards with the standard 52-cards
     */
    public Deck() {
        // Create a deck that is easier to pick from
        cardDeck = new ArrayList<>(52);

        // Create every single kind of card (A-K and hearts, diamonds, clubs, spades)
        for (int v = 0; v <= 12; v++) {
            for (int s = 0; s <= 3; s++) {
                PlayingCard playingCard = null;
                try {
                    playingCard = new PlayingCard(v, s);
                    cardDeck.add(playingCard);
                } catch (InvalidPlayingCardException e) {
                    e.printStackTrace();
                }
            }
        }

        // Shuffle the deck
        shuffleDeck();
    }

    /**
     * Creates a new Deck with a predefined size
     * @param size The size of the internal ArrayList that will be initialized
     */
    private Deck(int size) {
        cardDeck = new ArrayList<>(size);
    }

    /**
     * Shuffles the deck by randomly assigning card to indices
     */
    private void shuffleDeck() {
        Deck deck = new Deck(1);
        deck.mergeDeck(this);
        mergeDeck(deck);
    }

    /**
     * Draws a card from the deck by randomly picking an index, removing it, and returning the Card that was there
     * @return The Card object that was removed from the deck
     */
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

    /**
     * Draws n number of cards from the deck and returns an ArrayList containing all of the Cards
     * @param n The number of cards that should be drawn from the deck
     * @return An ArrayList containing all of the Cards that were drawn
     */
    public ArrayList<Card> drawCards(int n) {
        // Create an array of drawn cards
        ArrayList<Card> draws = new ArrayList<>();

        // Check to make sure there are enough cards
        if (cardDeck.size() < n) return null;

        // Loop through the array and draw a card for each position, returning null if the deck doesn't have enough left
        for (int i = 0; i < n; i++) draws.add(drawCard());
        return draws;
    }

    /**
     * Merges the given Deck with the one this method is called on and, inherently, shuffles the deck
     * @param d The Deck that will be merged into the other
     */
    private void mergeDeck(Deck d) {
        Card card;
        while ((card = d.drawCard()) != null) cardDeck.add(card);
    }

    /**
     * Returns the number of Cards that are in the ArrayList of Cards
     * @return The number of Cards in the ArrayList
     */
    public int getCardCount() {
        return cardDeck.size();
    }
}
