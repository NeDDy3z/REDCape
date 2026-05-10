package org.redcape.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

/**
 * LogConfig class for configuring logging in the application.
 * <p>
 * This class is responsible for enabling/disabling logging, configuring console and file handlers,
 * and managing log files.
 * </p>
 */
public final class Logging {

    /**
     * Private constructor to prevent instantiation.
     */
    private Logging() {}

    private static final Logger ROOT_LOGGER = Logger.getLogger("");
    private static final Logger BYPASS_LOGGER = createBypassLogger();

    /**
     * Disable all logging
     */
    public static void disableLogging() {
        // Remove all handlers
        for (Handler handler : ROOT_LOGGER.getHandlers()) {
            ROOT_LOGGER.removeHandler(handler);
        }
        ROOT_LOGGER.setLevel(Level.OFF);
    }

    /**
     * Enable console logging at INFO level
     */
    public static void enableLogging() {
        ROOT_LOGGER.setLevel(Level.INFO);

        // Remove existing handlers
        for (Handler handler : ROOT_LOGGER.getHandlers()) {
            ROOT_LOGGER.removeHandler(handler);
        }

        // Add console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter());
        ROOT_LOGGER.addHandler(consoleHandler);

        Logger.getLogger("org.redcape").info("Logging enabled");
    }

    /**
     * Enable file logging
     */
    public static void enableFileLogging() {
        String path = Directory.getLogsPath() + getNewLogFileName();

        // Configure file handler
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(path, true);
        } catch (IOException e) {
            Logging.bypassLog("Failed to create file handler to save log into file: " + e.getMessage());
            System.exit(1);
        }
        fileHandler.setLevel(Level.INFO);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        ROOT_LOGGER.addHandler(fileHandler);

        Logger.getLogger("org.redcape").info("File logging enabled - Logging to redcape.log");
    }

    /**
     * Delete old log files, keeping only the 5 most recent ones
     * This ensures the log directory does not fill up with old log files.
     */
    public static void deleteOldLogFiles() {
        String logDirPath = Directory.getLogsPath();
        java.io.File logDir = new java.io.File(logDirPath);
        if (!logDir.exists() || !logDir.isDirectory()) return;

        java.io.File[] logFiles = logDir.listFiles((dir, name) -> name.startsWith("redcape_") && name.endsWith(".log"));
        if (logFiles == null) return;

        // Keep only the 5 most recent log files
        java.util.Arrays.sort(logFiles, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
        for (int i = 4; i < logFiles.length; i++) {
            logFiles[i].delete();
        }
    }

    /**
     * Log a message that always appears, regardless of logging state.
     * @param message The message to log.
     */
    public static void bypassLog(String message) {
        BYPASS_LOGGER.log(Level.SEVERE, message);
    }

    /**
     * Create a logger that always appears, regardless of logging state.
     */
    private static Logger createBypassLogger() {
        Logger logger = Logger.getLogger("org.redcape.bypass");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        // Remove existing handlers
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

        return logger;
    }

    /**
     * Generate a new log file name with a timestamp
     *
     * @return a new log file name
     */
    private static String getNewLogFileName() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "redcape_" + timestamp + ".log";
    }
}