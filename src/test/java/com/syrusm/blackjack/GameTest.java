package com.syrusm.blackjack;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void testBasic(){
        Game.createInstance();
        Game game = Game.getInstance();
        game.init(true);

        assertTrue(game.getHumanPlayer() instanceof HumanPlayer);
        assertTrue(game.getHousePlayer() instanceof HouseAIPlayer);
    }

    @Test
    public void testGame(){
        Game.createInstance();
        Game game = Game.getInstance();
        game.init(false);

        boolean exited = false;
        try {
            game.run();
        } catch (ExitException e) {
            exited = true;
        }
        assertTrue(exited);
    }
}
