package com.syrusm.blackjack;

/**
 * A Controller's actions affects the player. For example, a HumanController allows a human to choose to Hit or Stay,
 * as well as exit the game. Similarly, An AIController causes a player to get another card or even exit the game.
 * @see HumanController, AIController
 */
public abstract class Controller {

    protected Player player;
    protected boolean isAI = false;

    public Controller(Player player) {
        this.player = player;
        player.setController(this);
    }

    /**
     * Actions that any controller can make.
     */
    public static enum Action {
        HIT,
        STAY
    }

    public boolean getIsAI() {
        return isAI;
    }

    /**
     * Used to make the controller do it's thinking and decide which Action to do.
     * @return action to be performed
     * @throws ExitException used when a player wants to exit the game
     */
    public Action play() throws ExitException {
        Action action = Action.STAY;

        // so long as card value is less than 21, a controller can hit another card and choose to stay
        int value = player.getHand().getValueBest();
        if (value < Hand.WIN_VALUE_21) {
            action = getAction();
        }

        return action;
    }

    /**
     * What should the player do?
     * @return Action that specified to Hit or Stay
     * @throws ExitException used when a player wants to exit the game
     */
    abstract protected Action getAction() throws ExitException;
}
