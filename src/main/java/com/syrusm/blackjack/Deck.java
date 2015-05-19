package com.syrusm.blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a standardized Deck of cards.
 */
public class Deck {
    // list of cards that will always remain the size of a standard size deck
    private ArrayList<Card> cards = new ArrayList<Card>(Card.Rank.values().length * Card.Suit.values().length);
    // refers to the top card
    private int topCardIndex = -1;

    /**
     * Creates Standard Size deck of 52 cards.
     */
    public Deck() {
        for (Card.Rank rank: Card.Rank.values()) {
            for (Card.Suit suit: Card.Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        this.topCardIndex = cards.size() - 1;
    }

    /**
     * Resets the deck and then shuffle the cards
     */
    public void shuffle() {
        for (Card card: cards) {
            card.setHidden(false);
        }
        this.topCardIndex = cards.size() - 1;
        Collections.shuffle(cards);
    }

    /**
     * returns a reference to car currently on top of the deck. Card is not 'popped' from the deck and simply
     * marked 'in play' so that it is not dealt again until the card is shuffled again.
     * @return next card
     */
    public Card getNextCard() {
        Card card = null;
        if (topCardIndex >= 0) {
            card = cards.get(topCardIndex);
            topCardIndex -= 1;
        }
        return card;
    }
}
