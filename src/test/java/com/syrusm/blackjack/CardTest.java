package com.syrusm.blackjack;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertTrue;


import java.util.ArrayList;

public class CardTest {

    private ArrayList<Card> cards = new ArrayList<Card>();
    private Card queenOfHearts;
    private Card aceOfSpades;

    @Before
    public void init() {
        for (Card.Suit suit: Card.Suit.values()) {
            for (Card.Rank rank: Card.Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }

        queenOfHearts = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS);
        aceOfSpades = new Card(Card.Rank.ACE, Card.Suit.SPADES);
    }

    @Test
    public void testSampleCards(){
        assertTrue(queenOfHearts.getSuit() == Card.Suit.HEARTS);
        assertTrue(queenOfHearts.getRank() == Card.Rank.QUEEN);

        assertTrue(aceOfSpades.getSuit() == Card.Suit.SPADES);
        assertTrue(aceOfSpades.getRank() == Card.Rank.ACE);
    }

    @Test
    public void testHiddenCard(){
        aceOfSpades.setHidden(!aceOfSpades.getHidden());
        String string = aceOfSpades.toString();
        // flip state back to whatever it was
        aceOfSpades.setHidden(!aceOfSpades.getHidden());

        // ensure the toStrings are different
        assertTrue(!string.equals(aceOfSpades.toString()));
    }

    @Test
    public void testToString(){
        for (Card card: cards) {
            assertTrue(!card.toString().isEmpty());
        }
    }
}
