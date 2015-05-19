package com.syrusm.blackjack;

/**
 * A Player represents one of the players in the game which include the house player. It is simply a container for
 * a Hand, name, and Controller.
 *
 * @see Hand
 * @see Controller
 */
public class Player {

    protected Hand hand;
    protected String name;
    protected Controller controller;

    public Player() {
        this("player");
    }

    public Player(String name) {
        hand = new Hand();
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public boolean isAI() {
        return controller != null && controller.getIsAI();
    }

    public String toString() {
        return name + ": " + hand;
    }
}
