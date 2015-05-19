package com.syrusm.blackjack;

/**
 * A Player can be controlled by a Human or an AI. The the latter case, an AIController is utilized.
 */
abstract public class AIController extends Controller {

    /**
     * When the AI player, delay in milliseconds is introduced to make it easier for a human player to follow the game
     */
    protected int delay = 2000;

    //Note: add other percepts / params shared by any ai controllers here

    public AIController(Player player) {
        super(player);
        isAI = true;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
