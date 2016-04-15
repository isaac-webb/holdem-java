package com.wildwebbs.java;

import java.util.ArrayList;

/**
 * HoldEmHand is a Hand specific to the game of Texas Hold 'Em. It represents the two card hand that a player has during the game and contains methods to deal with hand ranks.
 *
 * @author Isaac Webb
 * @version 1.1
 * @since 2016-04-12
 */
public class HoldEmHand extends Hand {
    /**
     * Creates a new HoldEmHand with a given array of initial Cards
     * @param initialCards The Cards that the Hand should contain
     */
    public HoldEmHand(ArrayList<Card> initialCards) {
        super(initialCards);
    }

    /**
     * Returns the rank of the hand when combined with the community cards. The array is employed when comparing hands and allows comparison of hands based on more than just pure rank 0-9. The meanings of the values change based on the hand rank.
     * @param tableCards The community cards for the round
     * @return The rank of the hand: [0] = overall rank, [1] = value of 1st match/straight/flush/high card, [2] = value of 2nd match/high card, [3] = value of high card
     */
    public int[] handRank(ArrayList<Card> tableCards) {
        // Will store the array for the best hand
        int[] max = new int[4];

        // Create an array to hold the table cards and the hand
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(tableCards);

        // Extract all of the cards from the hand
        ArrayList<Card> hand = new ArrayList<>();
        for (int v = 0; v < 13; v++) {
            ArrayList<Card> temp = getCardsOfValue(v);
            if (temp != null) hand.addAll(temp);
        }

        // Add the hand cards into the possible cards
        allCards.addAll(hand);

        // Iterate through all of the possible hands
        for (int a = 0; a < allCards.size() - 4; a++) {
            for (int b = a + 1; b < allCards.size() - 3; b++) {
                for (int c = b + 1; c < allCards.size() - 2; c++) {
                    for (int d = c + 1; d < allCards.size() - 1; d++) {
                        for (int e = d + 1; e < allCards.size(); e++) {
                            // Create an array using a combination of cards
                            ArrayList<Card> possibleHand = new ArrayList<>();
                            possibleHand.add(allCards.get(a));
                            possibleHand.add(allCards.get(b));
                            possibleHand.add(allCards.get(c));
                            possibleHand.add(allCards.get(d));
                            possibleHand.add(allCards.get(e));

                            // Get its rank and set max equal to it if it is better
                            int[] rankInfo = determineHandRank(possibleHand);
                            for (int i = 0; i < rankInfo.length; i++) {
                                if (rankInfo[i] > max[i]) {
                                    max = rankInfo;
                                    break;
                                } else if (rankInfo[i] < max[i]) break;
                            }
                        }
                    }
                }
            }
        }

        // Put the original cards back in the hand
        addCards(hand);

        // Return the best hand's information
        return max;
    }

    /**
     * Returns the rank information for the given hand. 0 - High Card, 1 - Pair, 2 - Two Pairs, 3 - Three of a Kind, 4 - Straight, 5 - Flush, 6 - Full House, 7 - Four of a Kind, 8 - Straight Flush, 9 - Royal Flush.
     * @param handCards The 5-card hand that should be evaluated
     * @return The rank of the hand: [0] = overall rank, [1] = value of 1st match/straight/flush/high card, [2] = value of 2nd match/high card, [3] = value of high card
     */
    private static int[] determineHandRank(ArrayList<Card> handCards) {
        HoldEmHand hand = new HoldEmHand(handCards);
        if (hand.getCardCount() != 5) {
            System.out.println("Please make sure you have five cards!");
            System.exit(1);
        }

        int[] rankInfo = {-1, -1, -1, -1};

        // Check for a flush or straight flush
        boolean foundFlush = false;
        for (int i = 0; i <= 3; i++) {
            // Check for flush
            if (hand.numberOfSuit(i) == 5) {
                foundFlush = true;
                break;
            }
        }

        // Check for a straight
        if ((rankInfo[1] = hand.containsStraight()) > -1) {
            // Return either 8 or 9 for straight flush and royal flush
            if (foundFlush) {
                if (hand.containsStraight() == 9) {
                    rankInfo[0] = 9;
                } else {
                    rankInfo[0] = 8;
                }
            } else {
                rankInfo[0] = 4;
            }
            return rankInfo;
        }

        // Count pairs, threes, or return a four of a kind
        ArrayList<Integer> pairs = new ArrayList<>();
        ArrayList<Integer> threes = new ArrayList<>();
        for (int i = 12; i >= 0; i--) {
            if (hand.numberOf(i) == 2) {
                pairs.add(i);
                continue;
            }
            if (hand.numberOf(i) == 3) {
                threes.add(i);
                continue;
            }
            if (hand.numberOf(i) == 4) {
                rankInfo[0] = 7;
                return rankInfo;
            }
        }

        if (threes.size() == 1) {
            // Full house or three of a kind
            if (pairs.size() == 1) {
                rankInfo[0] = 6;
                rankInfo[1] = threes.get(0);
                rankInfo[2] = pairs.get(0);
                return rankInfo;
            } else if (foundFlush) {
                // Safe to return a flush
                rankInfo[0] = 5;
                rankInfo[1] = hand.highCard();
                return rankInfo;
            } else {
                rankInfo[0] = 3;
                rankInfo[1] = threes.get(0);
                return rankInfo;
            }
        } else {
            // Two pair or pair
            if (pairs.size() == 2) {
                rankInfo[0] = 2;
                rankInfo[1] = pairs.get(0);
                rankInfo[2] = pairs.get(1);
                return rankInfo;
            }
            if (pairs.size() == 1) {
                rankInfo[0] = 1;
                rankInfo[1] = pairs.get(0);
                return rankInfo;
            }
        }

        // Ain't got nothin'
        rankInfo[0] = 0;
        rankInfo[1] = hand.highCard();
        return rankInfo;
    }

    /**
     * Looks to see if the current hand contains a straight and, if it does, returns the value of the first card in the straight.
     * @return The value of the first card in the straight or -1 if no straight exists
     */
    private int containsStraight() {
        // Go through each possible straight
        startLoop:
        for (int i = 0; i <= 9; i++) {
            for (int j = i; j < i + 5; j++) {
                if (numberOf(j % 13) != 1) continue startLoop;
            }
            return i;
        }
        return -1;
    }

    /**
     * Finds and returns the value of the highest card in the hand.
     * @return The value of the highest card
     */
    private int highCard() {
        for (int i = 12; i >= 0; i--) {
            if (numberOf(i) > 0) return i;
        }
        return -1;
    }

    /**
     * Adds a card into the hand, checking to make sure that the card is a proper playing card.
     * @param card The Card to be added into the Hand
     */
    @Override
    public void addCard(Card card) {
        if (card.getValue() >= 0 && card.getValue() <= 12 && card.getSuit() >= 0 && card.getSuit() <= 3) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card detected");
            System.exit(1);
        }
    }
}