package com.syrusm.blackjack;

import java.util.ArrayList;

public class Game {

    private static Game instance;

    Deck deck;
    ArrayList<Player> players;

    public static void createInstance() {
        instance = new Game();
    }

    public static Game getInstance() {
        return instance;
    }

    public Player getHousePlayer() {
        Player player = null;
        for (Player p: players) {
            if (p instanceof HouseAIPlayer) {
                player = p;
                break;
            }
        }
        return player;
    }

    public Player getHumanPlayer() {
        Player player = null;
        for (Player p: players) {
            if (p instanceof HumanPlayer) {
                player = p;
                break;
            }
        }
        return player;
    }

    public void init(boolean humanVsHouse) {
        deck = new Deck();
        players = new ArrayList(2);

        if (humanVsHouse) {
            players.add(new HumanPlayer("You"));
        } else {
            // if AI playing instead of human, run 10 times
            players.add(new HumanAIPlayer("HumanAI", 10));
        }

        //add house as last player since she needs to play last
        players.add(new HouseAIPlayer());
    }

    private void showPlayersHands(Player currentPlayer) {
        // make the player currently playing draw last to make it easier to follow when cards are added
        Player other = (currentPlayer == getHousePlayer())? getHumanPlayer():getHousePlayer();
        Console.showMessage(other.toString() + "\t\t" + currentPlayer.toString() + ", ?");
    }

    // game loop
    public void run() throws ExitException{
        //exit game with exception
        while (true) {
            runOneRound();
        }
    }

    public void runOneRound() throws ExitException{
        Player human = getHumanPlayer();
        Player house = getHousePlayer();

        Console.showMessage("\n\t--== Starting new game ==--");
        deck.shuffle();
        sleep(1000);
        Console.showMessage("\t  -- Shuffle Complete --");
        sleep(500);

        for (Player p : players) {
            p.getHand().clear();
        }

        // deal two cards to  human player
        human.getHand().addCard(deck.getNextCard());
        human.getHand().addCard(deck.getNextCard());

        // house player will receive cards last
        Card next = deck.getNextCard();
        next.setHidden(true);
        house.getHand().addCard(next);
        house.getHand().addCard(deck.getNextCard());

        // anyone lucky right off the bat on the first two dealt cards?
        if (house.getHand().getValueBest() == Hand.WIN_VALUE_21 ||
                human.getHand().getValueBest() == Hand.WIN_VALUE_21) {
            // round is over already
            house.getHand().getCard(0).setHidden(false);
            showPlayersHands(house);
        } else {
            // loop through all players. house is last
            for (Player p : players) {
                if (p == house) {
                    // once it is house's turn, show her first card
                    p.getHand().getCard(0).setHidden(false);
                    Console.showMessage("\n\t    -- " + p.getName() + "'s turn: --");
                }

                // show both players hands and then deal a card to current player
                while (true) {
                    showPlayersHands(p);

                    Player.Action action = p.play();

                    // if AI just played, act slow, a.k.a. a human.
                    if (p.getIsAI()) {
                        sleep(2000);
                    }

                    if (action == Player.Action.STAY) {
                        break;
                    }

                    p.getHand().addCard(deck.getNextCard());
                }

                // commentary on the new hand
                if (p.getHand().getValueLow() > Hand.WIN_VALUE_21) {
                    Console.showMessage("\tOops, " + p.getName() + " bust!");
                } else if (p.getHand().getValueBest() == Hand.WIN_VALUE_21) {
                    Console.showMessage("\tNice, " + p.getName() + " got 21!");
                }

            }
        }

        if (house.getHand().isBust() || human.getHand().isBust()) {
            if (!human.getHand().isBust()) {
                Console.showMessage("\tYou Win!");
            } else if (!house.getHand().isBust()){
                Console.showMessage("\tYou Lose");
            } else {
                // If both players bust, the dealer wins
                Console.showMessage("\tBoth bust, You Lost");
            }
        }else if (house.getHand().getValueBest() == human.getHand().getValueBest()) {
            // If both players have the same score, they tie
            Console.showMessage("\tThe round was a tie!");
        } else if (house.getHand().getValueBest() < human.getHand().getValueBest()) {
            Console.showMessage("\tYou Win!");
        } else {
            Console.showMessage("\tYou Lost");
        }
    }

    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        Game.createInstance();
        Game game = Game.getInstance();
        game.init(true);

        try {
            game.run();
        } catch (ExitException e) {
            System.exit(0);
        }

    }

}
