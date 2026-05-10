package org.redcape.model.level;

import org.redcape.util.Directory;

import java.util.HashMap;
import java.util.Map;


/**
 * Factory class for creating levels in the game
 * <p>
 * This class is responsible for creating and loading levels based on predefined types.
 * </p>
 */
public final class LevelFactory {

    private static final Map<String, String> predefinedLevels = new HashMap<>();

    // Predefined levels with their file paths
    static {
        predefinedLevels.put("default", "src/main/resources/levels/default.txt");
        predefinedLevels.put("easy", "src/main/resources/levels/easy.txt");
        // More levels can be added here
    }

    /**
     * Private constructor to prevent instantiation
     */
    private LevelFactory() {
    }

    /**
     * Creates a Level object based on a predefined level type
     *
     * @param levelName the name of the predefined level (e.g., "easy", "medium", "hard")
     * @return a Level object
     */
    public static Level loadLevel(String levelName) {
        String customLevelPath = Directory.getLevelsPath() + levelName.toLowerCase() + ".txt";
        java.io.File customLevelFile = new java.io.File(customLevelPath);

        if (customLevelFile.exists()) {
            return new Level(levelName, customLevelPath);
        }

        String levelPath = predefinedLevels.get(levelName.toLowerCase());
        if (levelPath == null) {
            // Fallback to default level if available
            String defaultPath = predefinedLevels.get("default");
            if (defaultPath != null) {
                return new Level("default", defaultPath);
            }
            throw new IllegalArgumentException("Unknown level name: " + levelName);
        }

        return new Level(levelName, levelPath);
    }

    /**
     * Returns the path to a predefined level file
     *
     * @param levelName of the required level
     * @return the path to the level file
     */
    public static String getLevelPath(String levelName) {
        return predefinedLevels.get(levelName.toLowerCase());
    }
}