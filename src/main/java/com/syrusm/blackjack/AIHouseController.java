package com.syrusm.blackjack;

/**
 * The Player playing as the house will be controlled by AIHouseController. Rules for the house player are
 * very specific and will be carried out here.
 */
public class AIHouseController extends AIController {

    public static final int MUST_STAY_MIN = 17;

    public AIHouseController(Player player) {
        super(player);
        player.setName("HouseAI");
        isAI = true;
    }

    protected Action getAction() {
        Action action = Action.HIT;
        int value = player.getHand().getValueBest();

        if (value >= MUST_STAY_MIN) {
            action = Action.STAY;
        }

        // act like I'm slow, a.k.a. a human
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (value >= Hand.WIN_VALUE_21) {
            action = Action.STAY;
        }
        return action;
    }

}
