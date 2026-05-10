package org.redcape.model.tile;

import org.redcape.util.FileHandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * TileFactory class is responsible for creating and managing tiles in the game.
 * <p>
 * This class provides methods to create different types of tiles based on their type.
 * </p>
 */
public final class TileFactory {

    /**
     * Tile sprites folder path
     */
    private static String TILE_SPRITES_FOLDER = "src/main/resources/tiles/";

    /**
     * Preloaded images for tiles
     */
    private static Map<String, BufferedImage> preloadedImages = new HashMap<>();

    /**
     * Private constructor to prevent instantiation
     */
    private TileFactory() {
    }

    /**
     * Creates a new tile based on type
     *
     * @param type name of the tile from tilemap txt - e.g., "g", "c",...
     * @return a new tile
     */
    public static Tile createTile(String type) {
        if (preloadedImages.isEmpty()) {
            loadImages();
        }

        return switch (type.toLowerCase()) {
            case "barrier", "x" -> getBarrier();
            case "camp", "c" -> getCamp();
            case "door", "d" -> getDoor();
            case "food", "f" -> getFood();
            case "grass", "g", "-", " ", "  " -> getGrass();
            case "key", "k" -> getKey();
            case "key_blade", "b" -> getKeyBlade();
            case "key_ring", "r" -> getKeyRing();
            case "potion", "p" -> getPotion();
            case "spawn" , "s" -> getSpawn();
            case "tree", "t" -> getTree();
            case "wolf", "w" -> getWolf();
            default -> getGrass();
        };
    }

    private static void loadImages() {
        File folder = new File(TILE_SPRITES_FOLDER);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
        if (files != null) {
            for (File file : files) {
                BufferedImage img = FileHandling.loadImage(file.getPath());
                if (img != null) {
                    preloadedImages.put(file.getName(), img);
                }
            }
        }
    }

    private static Tile getBarrier() {
        Tile tile = getGrass();

        tile.setCollision(true);

        return tile;
    }

    private static Tile getCamp() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.CAMP);

        return tile;
    }

    private static Tile getDoor() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.DOOR);
        tile.setSprite(getSprite("door.png"));

        return tile;
    }

    private static Tile getFood() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.FOOD);

        return tile;
    }

    private static Tile getGrass() {
        Tile tile = new Tile();

        tile.setSprite(getSprite("grass.png"));
        tile.setCollision(false);
        tile.setType(Tile.Type.GRASS);

        return tile;
    }

    private static Tile getKey() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.KEY);

        return tile;
    }

    private static Tile getKeyBlade() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.KEY_PART);
        tile.setName("Key Blade");

        return tile;
    }

    private static Tile getKeyRing() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.KEY_PART);
        tile.setName("Key Ring");

        return tile;
    }

    private static Tile getPotion() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.POTION);

        return tile;
    }

    private static Tile getSpawn() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.SPAWN);

        return tile;
    }

    private static Tile getTree() {
        Tile tile = new Tile();

        tile.setSprite(getSprite("tree.png"));
        tile.setCollision(true);
        tile.setType(Tile.Type.TREE);

        return tile;
    }

    private static Tile getWolf() {
        Tile tile = getGrass();
        tile.setType(Tile.Type.SPAWN_ENEMY);

        return tile;
    }

    private static BufferedImage getSprite(String tileFileName) {
        return preloadedImages.get(tileFileName);
    }
}
