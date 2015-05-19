package com.syrusm.blackjack;

/**
 * Exception used to signal that a player wants to leave the game
 */
public class ExitException extends Exception {
    public ExitException(String message) {
        super(message);
    }
}
