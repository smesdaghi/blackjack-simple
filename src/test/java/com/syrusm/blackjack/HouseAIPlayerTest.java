package com.syrusm.blackjack;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HouseAIPlayerTest {

    @Test
    public void testActions(){
        Player house = new Player();
        new AIHouseController(house);

        assertTrue(house.isAI());
        Hand hand = house.getHand();

        hand.addCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        hand.addCard(new Card(Card.Rank.SIX, Card.Suit.SPADES));

        boolean exited = false;
        try {
            assertTrue(house.getController().play() == Controller.Action.HIT);

            hand.addCard(new Card(Card.Rank.TWO, Card.Suit.SPADES));
            assertTrue(house.getController().play() == Controller.Action.STAY);

            hand.addCard(new Card(Card.Rank.THREE, Card.Suit.SPADES));
            assertTrue(hand.getValueBest() == Hand.WIN_VALUE_21);
            assertTrue(house.getController().play() == Controller.Action.STAY);

            hand.addCard(new Card(Card.Rank.TWO, Card.Suit.HEARTS));
            assertTrue(house.getController().play() == Controller.Action.STAY);
        } catch (ExitException e) {
            exited = true;
        }

        assertTrue(!exited);
    }
}
