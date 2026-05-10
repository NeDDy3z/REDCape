package org.redcape.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * GameSaveDTO is a data transfer object that holds the game state for saving and loading.
 * <p>
 * This class contains all the necessary information to save the game state in a human-readable format.
 * </p>
 */
@Getter
@NoArgsConstructor
public class GameSaveDTO {
    @JsonProperty("level")
    private LevelData level;
    
    @JsonProperty("player")
    private PlayerData player;
    
    @JsonProperty("enemy")
    private EnemyData enemy;

    /**
     * Constructor for GameSaveDTO
     *
     * @param levelName of the level
     * @param levelObjects in the level
     * @param playerPosition of the player in x,y
     * @param playerHealth of the player
     * @param itemsInInventory of the player
     * @param enemyPosition in x,y
     */
    public GameSaveDTO(String levelName, String[] levelObjects, int[] playerPosition,
                      int playerHealth, String[] itemsInInventory, int[] enemyPosition) {
        this.level = new LevelData(levelName, levelObjects);
        this.player = new PlayerData(playerPosition, playerHealth, itemsInInventory);
        this.enemy = new EnemyData(enemyPosition);
    }

    /**
     * Class representing the level data.
     */
    @Getter
    @NoArgsConstructor
    public static class LevelData {
        private String name;
        private String[] objects;

        /**
         * Constructor for LevelData
         *
         * @param name of the level
         * @param objects in the level
         */
        public LevelData(String name, String[] objects) {
            this.name = name;
            this.objects = objects;
        }
    }

    /**
     * Class representing the player data.
     */
    @Getter
    @NoArgsConstructor
    public static class PlayerData {
        private int[] position;
        private int health;
        private String[] inventory;

        /**
         * Constructor for PlayerData
         *
         * @param position of the player in x,y
         * @param health of the player
         * @param inventory of the player
         */
        public PlayerData(int[] position, int health, String[] inventory) {
            this.position = position;
            this.health = health;
            this.inventory = inventory;
        }
    }

    /**
     * Class representing the enemy data.
     */
    @Getter
    @NoArgsConstructor
    public static class EnemyData {
        private int[] position;

        /**
         * Constructor for EnemyData
         *
         * @param position of the enemy in x,y
         */
        public EnemyData(int[] position) {
            this.position = position;
        }
    }
}