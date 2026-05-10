package org.redcape.model.level;

import lombok.Getter;
import lombok.extern.java.Log;

import org.redcape.exceptions.ObjectNotInitialized;
import org.redcape.model.object.Item;
import org.redcape.model.tile.Tile;
import org.redcape.model.tile.TileFactory;
import org.redcape.model.object.ItemFactory;
import org.redcape.util.FileHandling;
import org.redcape.model.object.items.SuperItem;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Level class represents a game level.
 * <p>
 * This class is responsible for loading/processing the level from a file,
 * processing the level data, and managing the items in the level.
 * </p>
 */
@Log
@Getter
public final class Level {

    // Level details
    private final String levelName;
    private final String levelPath;

    // Level properties
    private Tile[][] tileMap;
    private ArrayList<SuperItem> levelObjects;
    private int[] playerSpawnPosition;
    private int[] enemySpawnPosition;


    /**
     * Constructor for the Level class
     *
     * @param levelName the name of the level
     * @param levelPath the path to the level file
     */
    public Level(String levelName, String levelPath) {
        this.levelName = levelName;
        this.levelPath = levelPath;
        this.levelObjects = new ArrayList<>();

        try {
            loadLevel();
        } catch (FileNotFoundException e) {
            log.severe("Level file not found: " + levelPath);
        } catch (ObjectNotInitialized e) {
            log.severe("Error while loading level file. Check level file for incorrect configuration. " + e.getMessage());
        }

    }


    /**
     * Process the objects for a set game panel
     * This function will set the correct position of the objects in the level
     *
     * @param tileSize the size of the tile
     */
    public void setLevelObjectPositions(int tileSize) {
        for (SuperItem object : levelObjects) {
            object.setPosition(object.getWorldX() * tileSize, object.getWorldY() * tileSize);
        }
    }

    /**
     * Return the level objects in a string array
     *
     * @return String array representation of the level objects
     */
    public String[] getLevelObjectsInString() {
        String[] objectsInString = new String[levelObjects.size()];

        for (int i = 0; i < levelObjects.size(); i++) {
            SuperItem object = levelObjects.get(i);

            boolean pickedUp = (object.getType() == Item.Type.CAMP) ? object.isUsed() : object.isPickedUp();

            objectsInString[i] = object.getName() +","+ pickedUp +","+ object.getWorldX() +","+ object.getWorldY();
        }

        return objectsInString;
    }

    /**
     * This function will load the level objects from a string
     * It is used in the GameSave (game loading) process
     *
     * @param objectsInString data
     * @see org.redcape.controller.GameSave
     */
    public void loadLevelObjects(String[] objectsInString) {
        levelObjects.clear();

        for (String objectInString : objectsInString) {
            String[] objectData = objectInString.split(",");

            String name = objectData[0];
            boolean pickedUp = Boolean.parseBoolean(objectData[1]);
            int x = Integer.parseInt(objectData[2]);
            int y = Integer.parseInt(objectData[3]);


            if (pickedUp) {
                levelObjects.add(ItemFactory.createPickedUpItem(name, x, y));
            } else {
                levelObjects.add(ItemFactory.createItem(name, x, y));
            }
        }
    }

    
    /**
     * Load level from a file
     *
     * @throws ObjectNotInitialized
     * @throws FileNotFoundException
     */
    private void loadLevel() throws ObjectNotInitialized, FileNotFoundException {
        String levelRaw = FileHandling.loadLevel(levelPath);

        if (levelRaw == null) {
            throw new FileNotFoundException("");
        }

        processLevel(levelRaw);
        processObjects();
    }

    /**
     * Load the level from the raw string
     *
     * @param levelRaw string representation of the level
     */
    private void processLevel(String levelRaw)  {
        String[] lines = levelRaw.split("\n");
        int rows = 128, cols = 128; // TODO replace hardcoded values with dynamic ones from level file

        // Set size
        tileMap = new Tile[rows][cols];

        int rowIdx = 0;
        for (String line : lines) {
            // Remove whitespace
            line = line.trim();

            // Ignore comments
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }

            // Process the grid lines (comma-separated)
            String[] tiles = line.split(", ");
            for (int colIdx = 0; colIdx < tiles.length; colIdx++) {
                Tile tile = TileFactory.createTile(tiles[colIdx]);
                tileMap[colIdx][rows - 1 - rowIdx] = tile; // Fix rotation: assign transposed and reversed row index
            }
            rowIdx++;
        }
    }

    /**
     * Process the objects in the level
     * Converts the tile map to a list of objects
     */
    private void processObjects() {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Tile tile = tileMap[i][j];
                if (tile != null) {
                    switch (tile.getType()) {
                        case Tile.Type.CAMP -> levelObjects.add(ItemFactory.createItem("Camp", i, j));
                        case Tile.Type.DOOR -> levelObjects.add(ItemFactory.createItem("End", i, j));
                        case Tile.Type.FOOD -> levelObjects.add(ItemFactory.createItem("Food", i, j));
                        case Tile.Type.POTION -> levelObjects.add(ItemFactory.createItem("Potion", i, j));
                        case Tile.Type.KEY -> levelObjects.add(ItemFactory.createItem("Key", i, j));
                        case Tile.Type.KEY_PART -> processKeyPartTile(tile, i, j);
                        case Tile.Type.SPAWN -> playerSpawnPosition = new int[]{i, j};
                        case Tile.Type.SPAWN_ENEMY -> enemySpawnPosition = new int[]{i, j};
                    }
                }
            }
        }
    }

    private void processKeyPartTile(Tile tile, int x, int y) {
        if (tile.getType() == Tile.Type.KEY_PART) {
            if (tile.getName().equals("Key Blade")) {
                levelObjects.add(ItemFactory.createItem("Key Blade", x, y));
            } else if (tile.getName().equals("Key Ring")) {
                levelObjects.add(ItemFactory.createItem("Key Ring", x, y));
            }

        }
    }
}
