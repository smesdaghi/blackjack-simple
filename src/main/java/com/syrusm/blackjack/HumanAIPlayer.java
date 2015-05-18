package com.syrusm.blackjack;

public class HumanAIPlayer extends HumanPlayer {

    private static int humanAICounter = 0;
    private int actionCounter = 0;
    private int actionMax = -1;

    public HumanAIPlayer(String name, int actionMax) {
        super(name);
        isAI = true;
        this.actionMax = actionMax;
    }

    public HumanAIPlayer() {
        this("HumanAI" + humanAICounter++, -1);
    }

    @Override
    protected Action getAction() throws ExitException{
        actionCounter++;

        if (actionMax != -1 && actionCounter > actionMax) {
            throw new ExitException("HumanAI is done");
        }

        Action action;

        if (hand.getValueBest() >= 18) {
            action = Action.STAY;
        } else if (hand.getValueBest() >= 14) {
            // a tiny bit of variation
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

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

}
