package org.redcape.view;

import lombok.Getter;
import lombok.Setter;

import lombok.extern.java.Log;
import org.redcape.model.tile.Tile;
import org.redcape.model.level.Level;

import java.awt.*;


/**
 * TileManager class for managing the tiles in the game
 * <p>
 * It handles the drawing of tiles based on the player's position and screen size.
 * </p>
 */
@Log
public final class TileManager {

    @Getter
    @Setter
    private Tile[][] tiles;

    // Classes
    private final GamePanel gamePanel;


    /**
     * Constructor
     *
     * @param gamePanel object
     * @param level object
     */
    public TileManager(GamePanel gamePanel, Level level) {
        this.gamePanel = gamePanel;

        tiles = level.getTileMap();
    }

    /**
     * Draw tiles on the game screen
     *
     * @param g2d object
     */
    public void draw(Graphics2D g2d) {
        if (tiles == null) return;

        // Helper variables
        int tileSize = gamePanel.getTileSize();
        int screenWidth = gamePanel.getScreenWidth();
        int screenHeight = gamePanel.getScreenHeight();
        int playerWorldX = gamePanel.getPlayer().getWorldX();
        int playerWorldY = gamePanel.getPlayer().getWorldY();

        // Render only the tiles that are within the screen bounds
        int startCol = Math.max(0, (playerWorldX - screenWidth / 2) / tileSize - 2);
        int endCol = Math.min(tiles[0].length, (playerWorldX + screenWidth / 2) / tileSize + 3);
        int startRow = Math.max(0, (playerWorldY - screenHeight / 2) / tileSize - 2);
        int endRow = Math.min(tiles.length, (playerWorldY + screenHeight / 2) / tileSize + 3);

        // Render the tiles
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int worldX = col * tileSize;
                int worldY = row * tileSize;
                int screenX = worldX - playerWorldX + gamePanel.getPlayer().getScreenX();
                int screenY = worldY - playerWorldY + gamePanel.getPlayer().getScreenY();

                g2d.drawImage(
                        tiles[col][row].getSprite(),
                        screenX,
                        screenY,
                        tileSize,
                        tileSize,
                        null
                );
            }
        }
    }
}
