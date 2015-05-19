package com.syrusm.blackjack;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void testBasic(){
        Game.createInstance();
        Game game = Game.getInstance();
        game.init(true);

        assertTrue(!(game.getPlayer().getController() instanceof AIHouseController));
        assertTrue(game.getHouse().getController() instanceof AIHouseController);
    }

    // run the game with AIPlayer vs AIHouse for a set number of runs
    // it can help with coverage as well as broader cases not easily caught
    // Note:
    @Test
    public void testGame(){
        Game.createInstance();
        Game game = Game.getInstance();
        game.init(false);

        Player house = game.getHouse();
        Player player = game.getPlayer();

        // when ai plays instead of the human, let him run 10 times.
        ((AIPlayerController)player.getController()).setPlayCounter(100);

        // do not slow down the tests
        ((AIController)game.getPlayer().getController()).setDelay(0);
        assertTrue(((AIController)player.getController()).getDelay() == 0);

        ((AIController)game.getHouse().getController()).setDelay(0);

        boolean exited = false;
        try {
            while(true) {
                Player winner = game.runOneRound();

                // tied, they need to have same exact value
                if (winner == null) {
                    assertTrue(player.getHand().getValueBest() == house.getHand().getValueBest());
                } else if (winner.getHand().isBust()) {
                    assertTrue(winner == house);
                } else {
                    Player other = (winner == house)? player: house;
                    if (!other.getHand().isBust()) {
                        assertTrue(winner.getHand().getValueBest() > other.getHand().getValueBest());
                    }
                }
            }
        } catch (ExitException e) {
            exited = true;
        }
        assertTrue(exited);
    }
}
