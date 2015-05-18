package com.syrusm.blackjack;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards = new ArrayList<Card>();
    public static final int WIN_VALUE_21 = 21;
    public static final int ACE_HIGH_VALUE = 11;
    public static final int PICTURE_VALUE = 10;

    public Hand() {
    }

    public void addCard(final Card card) {
        cards.add(card);
    }

    /**
     * returns the card at a given index if such card exists, otherwise returns null
     * @param index
     * @return
     */
    public Card getCard(int index) {
        Card card = null;
        if (index >= 0 && index <cards.size()){
            card = cards.get(index);
        }
        return card;
    };

    public void clear() {
        cards.clear();
    }

    /**
     * get the lowest possible value represented by hand by Assuming all Aces are '1'
     * @return
     */
    public int getValueLow() {
        int value = 0;
        for (Card card: cards) {
            value += evaluateRank(card.getRank(), false /* for now*/);
        }
        return value;
    }

    /**
     * get the 'best' value of the hand by figuring out how many aces to count as '1'
     * in order to make the hand as high as possible but equal to or less than 21
     * @return
     */
    public int getValueBest() {
        int aceCount = 0;
        int value = 0;
        for (Card card: cards) {
            value += evaluateRank(card.getRank(), true /* assume high ace */);
            if (card.getRank() == Card.Rank.ACE) {
                aceCount++;
            }
        }

        // if value of hand is greater than 21 by assuming all Aces were 11,
        // start replacing them by 1 until we are not over 21
        while (value > WIN_VALUE_21  && aceCount > 0) {
            value = value - evaluateRank(Card.Rank.ACE, true) + evaluateRank(Card.Rank.ACE, false);
            aceCount--;
        }

        return value;
    }

    public boolean isBust() {
        return getValueLow() > WIN_VALUE_21;
    }

    static private int evaluateRank(Card.Rank rank, boolean aceIsHighest) {
        int value = rank.getValue();

        if (rank != Card.Rank.ACE) {
            if (value > PICTURE_VALUE) {
                value = PICTURE_VALUE;
            }
        } else if (aceIsHighest) {
            value = ACE_HIGH_VALUE;
        }

        return value;
    }

    public String toString() {
        String hand = "";
        for (Card card: cards) {
            if (!hand.isEmpty()) {
                hand += ", ";
            }
            hand += card;
        }
        return hand;
    }
}
