package org.redcape.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.redcape.controller.GameState;
import org.redcape.controller.KeyController;
import org.redcape.model.level.Level;
import org.redcape.util.FileHandling;
import org.redcape.view.ui.screens.GameOver;
import org.redcape.view.ui.screens.Menu;
import org.redcape.view.ui.screens.Paused;
import org.redcape.view.ui.UIController;

import javax.swing.*;
import java.awt.*;


/**
 * GameWindow class for the game
 * <p>
 * This class is responsible for creating and managing the game window.
 * It handles the rendering of different screens and manages the key events.
 * </p>
 */
@Log
public final class GameWindow extends JFrame {

    // Parameters
    /**
     * Window width dimensions
     */
    public static final int WIDTH = 1280;

    /**
     * Window height dimension
     */
    public static final int HEIGHT = 720;

    // Game components
    private final GameState gameState;

    // Views
    @Getter
    @Setter
    private GamePanel gamePanel;

    // Classes
    private final UIController uiController;

    /**
     * Constructor for the GameWindow class
     *
     * @param keyController instance
     * @param level         instance
     * @param gameState     instance
     */
    public GameWindow(KeyController keyController, Level level, GameState gameState) {
        this.uiController = new UIController(this, keyController);

        // Set up the game window
        this.gamePanel = new GamePanel(level, gameState);
        this.gameState = gameState;

        uiController.addScreen("menu", new Menu(gameState));
        uiController.addScreen("paused", new Paused(gameState));
        uiController.addScreen("gameover", new GameOver(gameState, false));
        uiController.addScreen("gamewin", new GameOver(gameState, true));
        uiController.addScreen("playing", this.gamePanel);

        uiController.setActiveScreen("menu");
        buildWindow(keyController);
    }

    /**
     * Builds the window
     *
     * @param keyController instance
     */
    private void buildWindow(KeyController keyController) {
        // Set properties
        setTitle("RED:Cape");
        setName("RED:Cape");
        setIconImage(FileHandling.loadImage("src/main/resources/graphics/icon.png"));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setResizable(false);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addKeyListener(keyController);
    }

    /**
     * Opens the window
     */
    public void open() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Repaints the window
     */
    public void render() {
        decideScreen();
        uiController.repaint();
    }

    /**
     * Changes the screen based on the game state
     */
    private void decideScreen() {
        String screen = switch (gameState.getActiveState()) {
            case GameState.State.GAME_OVER -> "gameover";
            case GameState.State.GAME_WIN -> "gamewin";
            case GameState.State.MENU -> "menu";
            case GameState.State.NEW_GAME -> "playing";
            case GameState.State.PAUSED -> "paused";
            case GameState.State.PLAYING -> "playing";
            default -> "menu";
        };

        uiController.setActiveScreen(screen);
    }
}
