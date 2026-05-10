package org.redcape;

import lombok.extern.java.Log;
import org.redcape.controller.Game;
import org.redcape.util.Directory;
import org.redcape.util.Logging;

import java.io.IOException;


/**
 * Entry point for the RED:cape game application.
 * <p>
 * This class initializes game directories, processes command line arguments,
 * configures logging, and starts the main game loop.
 * </p>
 *
 * @author Erik Vaněk
 * @version 1.0
 */
@Log
public class Main {
    public static void main(String[] args) {
        // Setup helper variables
        boolean logging = false;
        boolean saveLog = false;
        boolean godMode = false;
        boolean debug = false;

        // Parse command line arguments
        for (String arg : args) {
            switch (arg) {
                case "--log":
                    logging = true;
                    break;
                case "--savelog":
                    saveLog = true;
                    break;
                case "--debug":
                    debug = true;
                    break;
                case "--godmode":
                    godMode = true;
                    break;
                case "--help":
                default:
                    System.out.println(
                            "Available commands: java -jar redcape.jar [--debug] [--godmode] [--help]\n" +
                                    "  --log        Enable logging into the console\n" +
                                    "  --savelog    Save console log to file (--log must be set too)\n" +
                                    "  --debug      Enable debug mode (draw entity hitboxes)\n" +
                                    "  --godmode    Enable god mode (walk all over the map, enemies don't hurt)\n" +
                                    "  --help       Show this help message\n"
                    );
                    System.exit(0);
                    break;
            }
        }


        // Enable logging if requested
        if (logging) {
            Logging.enableLogging();
            if (saveLog) {
                Directory.setLocalPaths(); // Set up local paths for game data
                Logging.deleteOldLogFiles();
                Logging.enableFileLogging();
            }
        } else {
            Logging.disableLogging();
        }

        // If paths weren't set already, it'll set them here
        if (!Directory.isLocalPathSet()) {
            Directory.setLocalPaths();
        }



        // Start game
        log.info("Starting RED:cape...");

        Game game = new Game();
        game.execute();

        // Set game parameter
        game.setGodMode(godMode);
        game.setDebug(debug);
    }
}