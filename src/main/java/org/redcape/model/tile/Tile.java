package org.redcape.model.tile;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Tile class represents a tile in the game.
 * It contains information about the tile's sprite, collision status, and type.
 */
@Getter
@Setter
public final class Tile extends JPanel {

    /**
     * Constructor for the Tile class
     */
    public Tile() {
    }

    /**
     * Tile type enum
     * <p>
     * This enum represents the different types of tiles in the game.
     * </p>
     */
    public enum Type {
        CAMP,
        DOOR,
        FOOD,
        GRASS,
        KEY,
        KEY_PART,
        POTION,
        SPAWN,
        SPAWN_ENEMY,
        TREE,
    }

    /**
     * Tile image sprite
     */
    private BufferedImage sprite;

    /**
     * Tile collision status
     * True = player cannot enter a tile
     */
    private boolean collision;

    /**
     * Tile type
     */
    private Type type;

    /**
     * Tile name
     */
    private String name;
}
