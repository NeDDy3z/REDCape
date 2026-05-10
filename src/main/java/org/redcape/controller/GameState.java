package org.redcape.controller;

import lombok.Getter;
import lombok.extern.java.Log;


/**
 * GameState class for managing the game state.
 * <p>
 * This class is responsible for managing the current state of the game, such as whether the game is paused,
 * in the menu, or in play mode.
 * </p>
 */
@Log
public final class GameState {

    /**
     * Enum representing the different states of the game.
     */
    public enum State {
        CONTINUE,
        GAME_OVER,
        GAME_WIN,
        MENU,
        NEW_GAME,
        PAUSED,
        PLAYING,
        SAVE,
    }

    @Getter
    private State activeState;

    /**
     * Constructor for GameState.
     *
     * @param activeState initial game state
     */
    public GameState(State activeState) {
        this.activeState = activeState;
    }

    /**
     * Sets the active game state.
     *
     * @param activeState new game state
     */
    public void setActiveState(State activeState) {
        log.info("Game state change: " + this.activeState + " -> " + activeState);
        this.activeState = activeState;
    }
}
