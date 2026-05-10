package org.redcape.model.tile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the TileFactory class.
 * <p>
 * This class contains tests to verify the correct creation of tiles
 * </p>
 */
class TileFactoryTest {

    @Nested
    @DisplayName("Tile Creation Tests")
    class TileCreationTests {

        @Test
        @DisplayName("Should create correct tile types from full names")
        void testCreateTileFromFullNames() {
            assertAll("Full name tile creation",
                    () -> assertEquals(Tile.Type.CAMP, TileFactory.createTile("camp").getType()),
                    () -> assertEquals(Tile.Type.DOOR, TileFactory.createTile("door").getType()),
                    () -> assertEquals(Tile.Type.FOOD, TileFactory.createTile("food").getType()),
                    () -> assertEquals(Tile.Type.GRASS, TileFactory.createTile("grass").getType()),
                    () -> assertEquals(Tile.Type.POTION, TileFactory.createTile("potion").getType()),
                    () -> assertEquals(Tile.Type.SPAWN, TileFactory.createTile("spawn").getType()),
                    () -> assertEquals(Tile.Type.TREE, TileFactory.createTile("tree").getType()),
                    () -> assertEquals(Tile.Type.SPAWN_ENEMY, TileFactory.createTile("wolf").getType())
            );
        }

        @Test
        @DisplayName("Should create correct tile types from shorthand notation")
        void testCreateTileFromShorthand() {
            assertAll("Shorthand tile creation",
                    () -> assertEquals(Tile.Type.CAMP, TileFactory.createTile("c").getType()),
                    () -> assertEquals(Tile.Type.DOOR, TileFactory.createTile("d").getType()),
                    () -> assertEquals(Tile.Type.FOOD, TileFactory.createTile("f").getType()),
                    () -> assertEquals(Tile.Type.GRASS, TileFactory.createTile("g").getType()),
                    () -> assertEquals(Tile.Type.POTION, TileFactory.createTile("p").getType()),
                    () -> assertEquals(Tile.Type.SPAWN, TileFactory.createTile("s").getType()),
                    () -> assertEquals(Tile.Type.TREE, TileFactory.createTile("t").getType()),
                    () -> assertEquals(Tile.Type.SPAWN_ENEMY, TileFactory.createTile("w").getType())
            );
        }

        @Test
        @DisplayName("Should create grass tiles from special characters")
        void testCreateGrassFromSpecialChars() {
            assertAll("Special character grass tiles",
                    () -> assertEquals(Tile.Type.GRASS, TileFactory.createTile("-").getType()),
                    () -> assertEquals(Tile.Type.GRASS, TileFactory.createTile(" ").getType()),
                    () -> assertEquals(Tile.Type.GRASS, TileFactory.createTile("  ").getType())
            );
        }
    }

    @Nested
    @DisplayName("Tile Property Tests")
    class TilePropertyTests {

        @Test
        @DisplayName("Grass tile should have correct properties")
        void testGrassTileProperties() {
            // When
            Tile grass = TileFactory.createTile("grass");

            // Then
            assertAll("Grass tile properties",
                    () -> assertFalse(grass.isCollision(), "Grass should not be collidable"),
                    () -> assertNotNull(grass.getSprite(), "Grass should have a sprite"),
                    () -> assertEquals(Tile.Type.GRASS, grass.getType(), "Should be grass type")
            );
        }

        @Test
        @DisplayName("Tree tile should have correct properties")
        void testTreeTileProperties() {
            // When
            Tile tree = TileFactory.createTile("tree");

            // Then
            assertAll("Tree tile properties",
                    () -> assertTrue(tree.isCollision(), "Tree should be collidable"),
                    () -> assertNotNull(tree.getSprite(), "Tree should have a sprite"),
                    () -> assertEquals(Tile.Type.TREE, tree.getType(), "Should be tree type")
            );
        }

        @Test
        @DisplayName("Door tile should have correct properties")
        void testDoorTileProperties() {
            // When
            Tile door = TileFactory.createTile("door");

            // Then
            assertAll("Door tile properties",
                    () -> assertNotNull(door.getSprite(), "Door should have a sprite"),
                    () -> assertEquals(Tile.Type.DOOR, door.getType(), "Should be door type")
            );
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {
        @Test
        @DisplayName("Should return grass tile for unknown tile type")
        void testUnknownTypeThrows() {
            Tile tile = TileFactory.createTile("unknown");
            assertEquals(Tile.Type.GRASS, tile.getType(),
                    "Empty string should create grass tile");
        }

        @Test
        @DisplayName("Should handle null tile type")
        void testNullTypeHandling() {
            assertThrows(NullPointerException.class,
                    () -> TileFactory.createTile(null),
                    "Should throw exception for null tile type"
            );
        }

        @Test
        @DisplayName("Should handle empty string tile type")
        void testEmptyStringHandling() {
            Tile tile = TileFactory.createTile("");
            assertEquals(Tile.Type.GRASS, tile.getType(),
                    "Empty string should create grass tile");
        }
    }
}