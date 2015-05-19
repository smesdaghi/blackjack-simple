package com.syrusm.blackjack;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HandTest {

    @Test
    public void testEmptyHand(){
        Hand hand = new Hand();
        assertTrue(hand.getValueLow() == 0);
        assertTrue(hand.toString().equals(""));

        hand.addCard(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        hand.clear();
        assertTrue(hand.getValueLow() == 0);
    }

    @Test
    public void testAA(){
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        hand.addCard(new Card(Card.Rank.ACE, Card.Suit.HEARTS));
        assertTrue(!hand.isBust());
        assertTrue(hand.getValueBest() == 12);
        assertTrue(hand.getValueLow() == 2);
        assertTrue(hand.getCard(0).getRank().getValue() == 1);
    }

    @Test
    public void testPicCards(){
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Rank.TWO, Card.Suit.SPADES));
        hand.addCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        hand.addCard(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        hand.addCard(new Card(Card.Rank.JACK, Card.Suit.SPADES));
        hand.addCard(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS));
        hand.addCard(new Card(Card.Rank.KING, Card.Suit.HEARTS));
        assertTrue(hand.getValueLow() == 43);
    }

    @Test
    public void testToString(){
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Rank.TWO, Card.Suit.SPADES));
        hand.addCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        assertNotNull(hand.toString());
        assertTrue(!hand.toString().equals(""));
    }
}
