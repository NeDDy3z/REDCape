package org.redcape.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.redcape.controller.CollisionController;
import org.redcape.controller.GameState;
import org.redcape.model.entity.Enemy;
import org.redcape.model.entity.Player;
import org.redcape.model.level.Level;
import org.redcape.model.object.items.SuperItem;
import org.redcape.view.ui.BaseUI;
import org.redcape.view.ui.screens.GUI;

import java.awt.*;


/**
 * GamePanel class for the game
 * <p>
 * This class is responsible for creating and managing the game panel.
 * It handles the rendering of the game objects and entities.
 * </p>
 */
@Getter
@Log
public final class GamePanel extends BaseUI {

    // Tile sizes
    private final int originalTileSize = 32;
    private final int scale = 2;
    private final int tileSize = originalTileSize * scale;

    // Window & screen dimensions
    /**
     * Window width dimensions
     */
    private final int screenWidth = GameWindow.WIDTH;
    /**
     * Window height dimension
     */
    private final int screenHeight = GameWindow.HEIGHT;

    // Classes
    private TileManager tileManager;
    private transient CollisionController collisionController;
    private GUI gui;

    // Game components
    private final GameState gameState;
    private Level level;
    @Setter
    private boolean debug;

    // Entities
    @Setter
    private Enemy enemy;
    private Player player;


    /**
     * Constructor for the GamePanel class
     *
     * @param level object
     * @param gameState object
     */
    public GamePanel(Level level, GameState gameState) {
        this.collisionController = new CollisionController(this);
        this.gameState = gameState;
        this.level = level;

        this.tileManager = new TileManager(this, level);

        build();
    }

    /**
     * Set player and instantiate GUI
     *
     * @param player object
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.gui = new GUI(player);
    }

    /**
     * Set level, instantiate tile manager and load level objects
     *
     * @param level object
     * @see Level
     */
    public void setLevel(Level level) {
        this.level = level;
        this.tileManager.setTiles(level.getTileMap());
        this.level.setLevelObjectPositions(tileSize);
    }


    /**
     * Instantiate the game panel with basic properties
     */
    @Override
    public void build() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
    }

    /**
     * Draw the game panel
     * <p>
     * * This method is responsible for rendering the game environment, objects and entities.
     * </p>
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw environment
        tileManager.draw(g2d);

        // Draw objects
        for (SuperItem object : level.getLevelObjects()) {
            if (object != null && !object.pickedUp) {
                object.draw(g2d, this);
                if (isDebug()) object.drawHitBox(g2d, this);
            }
        }

        // Draw entities
        player.draw(g2d);
        enemy.draw(g2d);

        // Draw GUI - inv, time, etc..
        gui.draw(g2d);

        g2d.dispose();
    }


    @Override
    public void addElements() {
        // Not used
    }

    @Override
    public void addActionListeners() {
        // Not used
    }

    /**
     * Display a message on the screen next to the inventory
     *
     * @param message to be displayed
     */
    public void showMessage(String message) {
        gui.showMessage(message);
    }
}
