package org.redcape.model.level;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class LevelFactoryTest {

    @Test
    void testCreateDefaultLevel() {
        Level level = LevelFactory.loadLevel("default");
        assertNotNull(level);
        assertEquals("default", level.getLevelName());
        assertNotNull(level.getTileMap());
    }

    @Test
    void testCreateNonexistentLevelThrows() {
        assertThrows(IllegalArgumentException.class, () -> LevelFactory.loadLevel("nonexistent"));
    }

    @Test
    void testLevelProperties() {
        Level level = LevelFactory.loadLevel("default");
        assertNotNull(level.getPlayerSpawnPosition());
        assertNotNull(level.getEnemySpawnPosition());
        assertNotNull(level.getLevelObjects());
    }
}
