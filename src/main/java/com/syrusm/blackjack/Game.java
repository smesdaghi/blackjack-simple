package com.syrusm.blackjack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 *  Entry point for the application, it can initialize and run a game.
 */
public class Game {

    // the single deck used for the game
    private Deck deck;
    // list of all Player instances which includes both house and human/ai player
    private ArrayList<Player> players;
    // Singleton instance
    private static Game instance;

    public static void createInstance() {
        instance = new Game();
    }

    public static Game getInstance() {
        return instance;
    }

    /**
     * @return the house player.
     */
    public Player getHouse() {
        Player player = null;
        for (Player p: players) {
            if (p.getController() instanceof AIHouseController) {
                player = p;
                break;
            }
        }
        return player;
    }

    /**
     * @return the non-house player a.k.a "the" player
     */
    public Player getPlayer() {
        Player player = null;
        for (Player p: players) {
            if (!(p.getController() instanceof AIHouseController)) {
                player = p;
                break;
            }
        }
        return player;
    }

    /**
     * create a player with a controller based on the provided controller class
     * @param name of the player
     * @param controllerClass type of controller to instantiate
     * @return player
     */

    public Player CreatePlayer(String name, Class<?> controllerClass) {
        Player player = new Player(name);
        Controller controller = null;
        try {
            Constructor constructor = controllerClass.getDeclaredConstructor(Player.class);
            constructor.setAccessible(true);
            controller = (Controller)constructor.newInstance(player);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // No controller, no player
        if (controller == null) {
            player = null;
        }

        return player;
    }

    /**
     * @param humanVsHouse when true, the player going against the house will be controlled
     *                     by a human. It will be AI controlled otherwise.
     */
    public void init(boolean humanVsHouse) {
        deck = new Deck();
        players = new ArrayList<Player>(2);

        if (humanVsHouse) {
            players.add(CreatePlayer("You", HumanController.class));
        } else {
            players.add(CreatePlayer("AIPlayer", AIPlayerController.class));
        }

        //add house as last player since she needs to play last
        players.add(CreatePlayer("AIHouse", AIHouseController.class));
    }

    private void showPlayersHands(Player currentPlayer) {
        // make the player currently playing draw last to make it easier to follow when cards are added
        Player other = (currentPlayer == getHouse())? getPlayer(): getHouse();
        Console.showMessage(other.toString() + "\t\t" + currentPlayer.toString());
    }

    // game loop
    public void run() throws ExitException{
        // only exit game when there is an exception
        while (true) {
            runOneRound();
        }
    }

    public Player runOneRound() throws ExitException{
        Player player = getPlayer();
        Player house = getHouse();

        Console.showMessage("\n\t--== Starting new game ==--");
        deck.shuffle();
        Console.showMessage("\t  -- Shuffle Complete --");

        for (Player p : players) {
            p.getHand().clear();
        }

        // deal two cards to  player
        player.getHand().addCard(deck.getNextCard());
        player.getHand().addCard(deck.getNextCard());

        // house player will receive cards last
        Card next = deck.getNextCard();
        next.setHidden(true);
        house.getHand().addCard(next);
        house.getHand().addCard(deck.getNextCard());

        // anyone lucky right off the bat on the first two dealt cards?
        if (house.getHand().getValueBest() == Hand.WIN_VALUE_21 ||
                player.getHand().getValueBest() == Hand.WIN_VALUE_21) {
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

                    Controller.Action action = p.getController().play();

                    if (action == Controller.Action.STAY) {
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

        Player winner = null;
        if (house.getHand().isBust() || player.getHand().isBust()) {
            if (!player.getHand().isBust()) {
                winner = player;
                Console.showMessage("\tYou Win!");
            } else if (!house.getHand().isBust()){
                winner = house;
                Console.showMessage("\tYou Lose");
            } else {
                // If both players bust, the house wins
                winner = house;
                Console.showMessage("\tBoth bust, You Lost");
            }
        } else if (house.getHand().getValueBest() == player.getHand().getValueBest()) {
            // If both players have the same score, they tie
            winner = null;
            Console.showMessage("\tThe round was a tie!");
        } else if (house.getHand().getValueBest() < player.getHand().getValueBest()) {
            winner = player;
            Console.showMessage("\tYou Win!");
        } else {
            winner = house;
            Console.showMessage("\tYou Lost");
        }

        return winner;
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
