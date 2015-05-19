package com.syrusm.blackjack;

/**
 * The same way a human can be playing the game, an AI can play. It can help with testing as well
 * as coverage.
 */
public class AIPlayerController extends AIController {

    /**
     * How many more times this AI will play before leaving the game
     */
    private int playCounter = -1;

    public AIPlayerController(Player player) {
        super(player);
        player.setName("PlayerAI");
    }

    /**
     * set PlayerCounter
     * @param playCounter number of times to player before exiting
     */
    public void setPlayCounter(int playCounter) {
        this.playCounter = playCounter;
    }

    protected Action getAction() throws ExitException{

        if (playCounter > 0) {
            playCounter--;
        } else if (playCounter == 0) {
            Console.showMessage("HumanAI is done");
            throw new ExitException("HumanAI exiting");
        }

        Action action;
        if (player.getHand().getValueBest() >= 18) {
            action = Action.STAY;
        } else if (player.getHand().getValueBest() >= 14) {

            // a tiny bit of variation, dont be perfect about it
            if (getRandomBoolean()) {
                action = Action.HIT;
            } else {
                action = Action.STAY;
            }
        } else {
            action = Action.HIT;
        }

        return action;
    }

    static protected boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

}
