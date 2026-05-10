package org.redcape.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * KeyController class for handling key events in the game.
 * <p>
 * This class implements the KeyListener interface to handle key events such as key presses and releases.
 * It manages player movement and game actions based on key inputs.
 * </p>
 */
@Getter
@Setter
@Log
public final class KeyController implements KeyListener {
    // KeyCont. components
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private boolean item1, item2, item3, item4, item5;

    // Game components
    private final GameState gameState;


    /**
     * Constructor for KeyController
     *
     * @param gameState class to set game state
     */
    public KeyController(GameState gameState) {
        this.gameState = gameState;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        decideMovement(code, true);
        decideAction(code);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        decideMovement(code, false);
    }

    /**
     * Return which item the player wants to use
     *
     * @param index of the item use
     * @return true if the item is wanted to be used
     */
    public boolean isItem(int index) {
        return switch (index) {
            case 1 -> item1;
            case 2 -> item2;
            case 3 -> item3;
            case 4 -> item4;
            case 5 -> item5;
            default -> false;
        };
    }


    /**
     * Reset item use flags
     */
    public void resetItemFlags() {
        item1 = false;
        item2 = false;
        item3 = false;
        item4 = false;
        item5 = false;
    }

    /**
     * Decide movement direction
     *
     * @param code  keycode
     * @param state movement state
     */
    private void decideMovement(int code, boolean state) {
        // Set movement direction
        switch (code) {
            case KeyEvent.VK_W -> moveUp = state;
            case KeyEvent.VK_S -> moveDown = state;
            case KeyEvent.VK_A -> moveLeft = state;
            case KeyEvent.VK_D -> moveRight = state;
        }
    }

    /**
     * Decide game action like pause game, or item interaction
     *
     * @param code keycode
     */
    private void decideAction(int code) {
        switch (code) {
            case KeyEvent.VK_ESCAPE -> setPause();
            case KeyEvent.VK_1 -> item1 = true;
            case KeyEvent.VK_2 -> item2 = true;
            case KeyEvent.VK_3 -> item3 = true;
            case KeyEvent.VK_4 -> item4 = true;
            case KeyEvent.VK_5 -> item5 = true;
        }
    }

    private void setPause() {
        if (gameState.getActiveState() == GameState.State.PLAYING) {
            gameState.setActiveState(GameState.State.PAUSED);
        } else if (gameState.getActiveState() == GameState.State.PAUSED) {
            gameState.setActiveState(GameState.State.PLAYING);
        }
    }
}
