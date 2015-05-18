package com.syrusm.blackjack;


public class HumanPlayer extends Player {

    private static int humanCounter = 0;

    public HumanPlayer(String name) {
        super(name);
    }

    public HumanPlayer() {
        this("Human " + humanCounter++);
    }

    public Action play() throws ExitException{
        Action action = Action.STAY;

        int value = getHand().getValueBest();
        if (value < Hand.WIN_VALUE_21) {
            action = getAction();
        }

        return action;
    }

    protected Action getAction() throws ExitException{
        Action action;

        while (true) {
            Console.showMessage("\tIt is your turn, 'H' to Hit or 'S' to Stay, 'Q' to quit: ");
            String input = Console.getUserInput();

            if (input.equalsIgnoreCase("H")) {
                action = Action.HIT;
                break;
            } else if (input.equalsIgnoreCase("S")) {
                action = Action.STAY;
                break;
            } else if (input.equalsIgnoreCase("Q")) {
                throw new ExitException("User request exit");
            }
        }

        return action;
    }
}
