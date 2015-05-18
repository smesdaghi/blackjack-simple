package com.syrusm.blackjack;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DeckTest {
    private Deck deck;

    @Before
    public void init() {
        deck = new Deck();
    }

    @Test
    public void testDeckSize(){
        int cardCount = 0;
        while (deck.getNextCard() != null){
            cardCount++;
        }
        assertTrue(cardCount == 52);

        deck.shuffle();
        assertTrue(cardCount == 52);
    }

    @Test
    public void testDeckReset(){
        // get few cards
        deck.getNextCard();
        deck.getNextCard();
        deck.getNextCard();

        deck.shuffle();

        int cardCount = 0;
        while (deck.getNextCard() != null){
            cardCount++;
        }
        assertTrue(cardCount == 52);
    }

    @Test
    public void testToString(){
        assertNotNull(deck.toString());
    }
}
