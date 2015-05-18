package com.syrusm.blackjack;

public class HouseAIPlayer extends Player {

    public static final int MUST_STAY_MIN = 17;

    public HouseAIPlayer() {
        hand = new Hand();
        name = "House";
        isAI = true;
    }

    // pass in other players
    public Action play() {
        Action action = Action.HIT;
        int value = getHand().getValueBest();

        if (value >= MUST_STAY_MIN) {
            action = Action.STAY;
        }

        // act like I'm slow, a.k.a. a human. even during the tests...
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (value >= Hand.WIN_VALUE_21) {
            action = Action.STAY;
        }
        return action;
    }

}
