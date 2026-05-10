package org.redcape.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.java.Log;

import org.redcape.model.GameSaveDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * FileHandling class for managing file operations.
 * <p>
 * This class is responsible for loading images, levels, and game state files,
 * as well as saving game state to files.
 * </p>
 */
@Log
public final class FileHandling {

    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Private constructor to prevent instantiation.
     */
    private FileHandling() {}


    /**
     * Copy a file from source to destination.
     * <p>
     * * This method copies a file from the source path to the destination path.
     * * It creates the destination file if it does not exist.
     * * </p>
     *
     * @param sourcePath the path to the source file
     * @param destinationPath the path to the destination file
     */
    public static void copyFile(String sourcePath, String destinationPath) {
        File destFile = new File(destinationPath);
        try (InputStream in = new FileInputStream(sourcePath)) {
            File parent = destFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            try (OutputStream out = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            log.info("Copied file from " + sourcePath + " to " + destinationPath);
        } catch (IOException e) {
            log.severe("Error while copying file: " + e.getMessage());
        }
    }

    /**
     * Call readFileAsImage and return BufferedImage from the file, whilst checking for exceptions.
     *
     * @param filePath to the image
     * @return BufferedImage
     */
    public static BufferedImage loadImage(String filePath) {
        try {
            return readFileAsImage(filePath);
        } catch (Exception e) {
            log.severe("Error while reading an image file: " + e.getMessage() + " - " + filePath);
            return null;
        }
    }

    /**
     * Call readFileAsString and return String from the file, whilst checking for exceptions.
     *
     * @param filePath to the level
     * @return String
     */
    public static String loadLevel(String filePath) {
        log.info("Loading level from " + filePath);

        try {
            return readFileAsString(filePath);
        } catch (IOException e) {
            log.severe("Error while reading a level file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves the game state to a JSON file.
     *
     * @param filePath path to the save file
     * @param data     game state data
     */
    public static void saveGameFile(String filePath, GameSaveDTO data) {
        createFile(filePath);

        try {
            mapper.writeValue(new File(filePath), data);
            log.info("Saved game state to " + filePath);
        } catch (Exception e) {
            log.severe("Error while saving game: " + e.getMessage());
        }
    }

    /**
     * Loads the game state from a JSON file.
     *
     * @param filePath path to the save file
     * @return GameSaveDTO object containing the game state
     */
    public static GameSaveDTO loadGameFile(String filePath) {
        try {
            return mapper.readValue(new File(filePath), GameSaveDTO.class);
        } catch (IOException e) {
            log.severe("Error while loading game: " + e.getMessage());
            return null;
        }
    }

    /**
     * Reads a file and returns its content as a String.
     *
     * @param filePath path to the file
     * @return content of the file
     * @throws IOException if a file cannot be read
     */
    private static String readFileAsString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                content.append(new String(buffer, 0, bytesRead));
            }
        }

        return content.toString();
    }

    /**
     * Reads the file and returns it as BufferedImage.
     *
     * @param filePath path to the image
     * @return BufferedImage
     * @throws IOException if a file cannot be read
     */
    private static BufferedImage readFileAsImage(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    /**
     * Create a file and directories to it based on the file path.
     *
     * @param filePath to file
     */
    public static void createFile(String filePath) {
        try {
            File file = new File(filePath.replaceFirst("^~", System.getProperty("user.home")));
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            log.severe("Error while creating file: " + e.getMessage());
        }
    }
}