package org.redcape.controller;

import lombok.extern.java.Log;

import org.redcape.model.GameSaveDTO;
import org.redcape.model.entity.Enemy;
import org.redcape.model.entity.Player;
import org.redcape.model.level.Level;
import org.redcape.model.level.LevelFactory;
import org.redcape.model.object.Inventory;
import org.redcape.util.Directory;
import org.redcape.util.FileHandling;
import org.redcape.view.GamePanel;


/**
 * GameSave class is responsible for saving and loading the game state.
 * <p>
 * This class handles the serialization and deserialization of the game "state" to and from a file.
 * </p>
 *
 * @see GameSaveDTO
 */
@Log
public class GameSave {

    /**
     * Path to the save file.
     */
    public final static String SAVE_FILE_PATH = Directory.getSavesPath() + "save.dat";


    /**
     * Private constructor to prevent instantiation.
     */
    private GameSave() {
    }

    /**
     * Saves the game state to a file.
     *
     * @param game The game object to save.
     */
    public static void saveGame(Game game) {
        // Helper variables
        GamePanel gamePanel = game.getWindow().getGamePanel();
        Level level = game.getLevel();
        Player player = game.getPlayer();
        Inventory inventory = player.getInventory();
        Enemy enemy = game.getEnemy();

        GameSaveDTO data = new GameSaveDTO(
                level.getLevelName(),
                level.getLevelObjectsInString(),
                player.getPosition(),
                player.getHealth(),
                inventory.getItemsInString(),
                enemy.getPosition()
        );

        FileHandling.createFile(SAVE_FILE_PATH);
        FileHandling.saveGameFile(SAVE_FILE_PATH, data);

        log.info("Game saved to file: " + SAVE_FILE_PATH);
    }

    /**
     * Loads the game state from a file.
     *
     * @param game The game object to load data into
     */
    public static void loadGame(Game game) {
        GameSaveDTO data = FileHandling.loadGameFile(SAVE_FILE_PATH);

        if (data == null) {
            game.getGameState().setActiveState(GameState.State.NEW_GAME);
            return;
        }

        // Helper variables
        GamePanel gamePanel = game.getWindow().getGamePanel();
        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        // Load level data
        GameSaveDTO.LevelData levelData = data.getLevel();
        game.setLevelName(levelData.getName());
        gamePanel.setLevel(LevelFactory.loadLevel(levelData.getName()));
        gamePanel.getLevel().loadLevelObjects(levelData.getObjects());

        // Load player data
        GameSaveDTO.PlayerData playerData = data.getPlayer();
        player.setWorldPosition(playerData.getPosition());
        player.setHealth(playerData.getHealth());
        player.getInventory().loadItems(playerData.getInventory());

        // Load enemy data
        GameSaveDTO.EnemyData enemyData = data.getEnemy();
        enemy.setWorldPosition(enemyData.getPosition());

        log.info("Game loaded from file: " + SAVE_FILE_PATH);
    }

}
