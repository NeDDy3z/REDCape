package org.redcape.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.redcape.model.entity.Enemy;
import org.redcape.model.entity.Player;
import org.redcape.model.level.Level;
import org.redcape.model.level.LevelFactory;
import org.redcape.util.Logging;
import org.redcape.view.GameWindow;


/**
 * Game class for the game
 * <p>
 * This class is responsible for managing the game state, game loop, and rendering the game.
 * </p>
 */
@Log
@Getter
@Setter
public final class Game implements Runnable {

    private static final int FPS = 60;
    private boolean debug = false;

    // Game components
    private String levelName = "default";
    private Level level;

    private Enemy enemy;
    private Player player;

    // Classes
    private final Thread gameThread;
    private final KeyController keyController;
    private final GameState gameState;
    private final GameWindow window;


    /**
     * Constructor for the game main class
     */
    public Game() {
        // Build world
        loadLevel();

        // Game components
        gameThread = new Thread(this);
        gameState = new GameState(GameState.State.MENU);
        keyController = new KeyController(gameState);

        window = new GameWindow(keyController, level, gameState);

        player = new Player(window.getGamePanel(), keyController);
        enemy = new Enemy(window.getGamePanel());

        window.getGamePanel().setPlayer(player);
        window.getGamePanel().setEnemy(enemy);
    }


    /**
     * Executes the game
     */
    public void execute() {
        gameThread.start();
        window.open();

        log.info("Game started!");
    }

    /**
     * Main method for the game loop
     */
    @Override
    public void run() {
        // Helper variables
        double drawInterval = (double) 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread.isAlive()) {
            try {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if (delta >= 1) {
                    // Main game loop being triggered by FPS

                    continueGame();
                    newGame();
                    playingGame();
                    saveGame();
                    gameEnded();

                    window.render();
                    delta--;
                }
            } catch (Exception e) {
                log.severe(e.getMessage());
            }
        }
    }

    /**
     * Updates the game entities
     */
    private void updateEntities() {
        player.update();
        enemy.update();

        if (player.isDead()) {
            gameState.setActiveState(GameState.State.GAME_OVER);
        }
    }

    /**
     * Load level based on level name
     */
    public void loadLevel() {
        try {
            level = LevelFactory.loadLevel(levelName);
            if (window != null) {
                window.getGamePanel().setLevel(level);
                log.info("Level loaded: " + level.getLevelName());
            }
        } catch (Exception e) {
            Logging.bypassLog("Failed to load the level - Exiting game: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Set player-to-god mode based on console argument
     *
     * @param godMode bool
     */
    public void setGodMode(boolean godMode) {
        player.setGodMode(godMode);
        log.info("God mode: " + godMode);
    }

    /**
     * Set player-to-debug mode based on console argument
     *
     * @param debug bool
     */
    public void setDebug(boolean debug) {
        window.getGamePanel().setDebug(debug);
        player.setDebug(debug);
        enemy.setDebug(debug);
    }

    /**
     * Continues the game - loads the last saved game
     */
    private void continueGame() {
        if (gameState.getActiveState() == GameState.State.CONTINUE) {
            GameSave.loadGame(this);

            gameState.setActiveState(GameState.State.PLAYING);
        }
    }

    /**
     * Starts a new game - initializes the player and enemy positions
     */
    private void newGame() {
        if (gameState.getActiveState() == GameState.State.NEW_GAME) {
            loadLevel();

            player = new Player(window.getGamePanel(), keyController);
            enemy = new Enemy(window.getGamePanel());

            window.getGamePanel().setPlayer(player);
            window.getGamePanel().setEnemy(enemy);

            player.setDefaultPosition(level.getPlayerSpawnPosition()[0], level.getPlayerSpawnPosition()[1]);
            enemy.setDefaultPosition(level.getEnemySpawnPosition()[0], level.getEnemySpawnPosition()[1]);

            gameState.setActiveState(GameState.State.PLAYING);
            window.getGamePanel().showMessage("Your adventure begins! - find the key parts and escape the forest");
        }
    }

    /**
     * Ensure the game is getting updated only in the playing state
     */
    private void playingGame() {
        if (gameState.getActiveState() == GameState.State.PLAYING) {
            updateEntities();
        }
    }

    /**
     * Save game on camp fire
     */
    private void saveGame() {
        if (gameState.getActiveState() == GameState.State.SAVE) {

            GameSave.saveGame(this);

            gameState.setActiveState(GameState.State.PLAYING);
        }
    }

    /**
     * Check for game over or game won
     */
    private void gameEnded() {
        if (gameState.getActiveState() == GameState.State.GAME_OVER) {
            // Do nothing - features not implemented (yet)
            return;
        }
    }
}
