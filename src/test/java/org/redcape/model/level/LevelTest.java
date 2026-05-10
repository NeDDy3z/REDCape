package org.redcape.model.level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redcape.model.tile.Tile;

import static org.junit.jupiter.api.Assertions.*;


class LevelTest {

    private Level level;

    @BeforeEach
    void setUp() {
        level = new Level("test", "src/test/java/org/redcape/model/level/testlevel.txt");
    }

    @Test
    void testLevelName() {
        assertEquals("test", level.getLevelName());
    }

    @Test
    void testTileMapNotNull() {
        assertNotNull(level.getTileMap());
        assertTrue(level.getTileMap().length > 0);
    }

    @Test
    void testPlayerSpawnPosition() {
        int[] spawn = level.getPlayerSpawnPosition();
        assertNotNull(spawn);
        assertEquals(2, spawn.length);
        // Optionally check that the spawn is within map bounds
        assertTrue(spawn[0] >= 0 && spawn[1] >= 0);
    }

    @Test
    void testEnemySpawnPosition() {
        int[] spawn = level.getEnemySpawnPosition();
        assertNotNull(spawn);
        assertEquals(2, spawn.length);
        assertTrue(spawn[0] >= 0 && spawn[1] >= 0);
    }

    @Test
    void testLevelObjects() {
        assertNotNull(level.getLevelObjects());
        // Items should be present if the test level contains item tiles
    }

    @Test
    void testTileTypesInMap() {
        Tile[][] map = level.getTileMap();
        boolean foundGrass = false;
        for (Tile[] row : map) {
            for (Tile tile : row) {
                if (tile != null && tile.getType() == Tile.Type.GRASS) {
                    foundGrass = true;
                }
            }
        }
        assertTrue(foundGrass, "At least one grass tile should exist in the map");
    }
}