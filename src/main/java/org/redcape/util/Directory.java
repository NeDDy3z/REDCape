package org.redcape.util;

import lombok.extern.java.Log;
import org.redcape.model.level.LevelFactory;


/**
 * Directory class for managing the game directory.
 * <p>
 * This class is responsible for setting the local paths for the game,
 * and providing methods to get the paths for logs and saves.
 * </p>
 */
@Log
public class Directory {

    /**
     * Private constructor to prevent instantiation.
     */
    private Directory() {}

    private static String LOCAL_PATH = null;
    private static final String GAME_DIRECTORY = "REDCape/";

    private static final String LEVELS = "levels/";
    private static final String LOGS = "logs/";
    private static final String SAVES = "saves/";

    /**
     * Set local paths based on the operating system.
     */
    public static void setLocalPaths() {
        String os = System.getProperty("os.name").toLowerCase();

        LOCAL_PATH = System.getProperty("user.home");

        // Set the local path based on the operating system
        if (os.contains("mac")) {
            LOCAL_PATH += "/Library/Application Support/";
        } else if (os.contains("win")) {
            LOCAL_PATH += "\\AppData\\Roaming\\";
        } else if (os.contains("nux") || os.contains("nix")) {
            LOCAL_PATH += "/.local/share/";
        }

        // Ensure directories exists
        new java.io.File(LOCAL_PATH + GAME_DIRECTORY);
        new java.io.File(LOCAL_PATH + GAME_DIRECTORY + LOGS);
        new java.io.File(LOCAL_PATH + GAME_DIRECTORY + SAVES);

        // Copy default files over
        FileHandling.copyFile(LevelFactory.getLevelPath("default"), getLevelsPath() + "default.txt");

        log.info("Game save path set to: " + LOCAL_PATH + GAME_DIRECTORY);
    }

    /**
     * Get the local path to the game directory.
     *
     * @return local path
     */
    public static String getLocalPath() {
        return LOCAL_PATH + GAME_DIRECTORY;
    }

    /**
     * Get the path to the levels directory.
     *
     * @return levels path
     */
    public static String getLevelsPath() {
        return getLocalPath() + LEVELS;
    }

    /**
     * Get the path to the logs directory.
     *
     * @return logs path
     */
    public static String getLogsPath() {
        return getLocalPath() + LOGS;
    }

    /**
     * Get the path to the saves directory.
     *
     * @return saves path
     */
    public static String getSavesPath() {
        return getLocalPath() + SAVES;
    }

    /**
     * Check if the local path is set.
     *
     * @return true if the local path is set, false otherwise
     */
    public static boolean isLocalPathSet() {
        return LOCAL_PATH != null;
    }
}
