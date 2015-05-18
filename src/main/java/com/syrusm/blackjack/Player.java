package com.syrusm.blackjack;

public abstract class Player {
    protected Hand hand;
    protected String name;
    protected boolean isAI = false;

    public static enum Action {
        HIT,
        STAY
    }

    public Player() {
        this("Unnamed");
    }

    public Player(String name) {
        hand = new Hand();
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean getIsAI() {
        return isAI;
    }

    public String getName() {
        return name;
    }

    public abstract Action play() throws ExitException;

    public String toString() {
        return name + ": " + hand;
    }
}
