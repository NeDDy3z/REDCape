package org.redcape.model.tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for the Tile class.
 * <p>
 * * This test verifies the functionality of the Tile class, including
 * </p>
 */
class TileTest {
    private Tile tile;
    private static final int TEST_SPRITE_SIZE = 16;

    @BeforeEach
    void setUp() {
        tile = new Tile();
    }

    @Nested
    @DisplayName("Collision Property Tests")
    class CollisionTests {
        @Test
        @DisplayName("Should correctly set and get collision property")
        void testCollisionProperty() {
            assertAll("Collision state changes",
                () -> {
                    tile.setCollision(true);
                    assertTrue(tile.isCollision(), "Tile should be collidable when set to true");
                },
                () -> {
                    tile.setCollision(false);
                    assertFalse(tile.isCollision(), "Tile should not be collidable when set to false");
                }
            );
        }

        @Test
        @DisplayName("Should maintain collision state after multiple changes")
        void testCollisionStateConsistency() {
            // When changing collision state multiple times
            tile.setCollision(true);
            tile.setCollision(false);
            tile.setCollision(true);
            
            // Then final state should be preserved
            assertTrue(tile.isCollision(), "Final collision state should be maintained");
        }
    }

    @Nested
    @DisplayName("Type Property Tests")
    class TypeTests {
        @Test
        @DisplayName("Should correctly set and get tile type")
        void testTypeProperty() {
            // When
            tile.setType(Tile.Type.FOOD);
            
            // Then
            assertEquals(Tile.Type.FOOD, tile.getType(), 
                "Tile type should match set value");
        }

        @Test
        @DisplayName("Should handle all tile types")
        void testAllTileTypes() {
            for (Tile.Type type : Tile.Type.values()) {
                // When
                tile.setType(type);
                
                // Then
                assertEquals(type, tile.getType(), 
                    "Tile should support type: " + type);
            }
        }
    }

    @Nested
    @DisplayName("Sprite Tests")
    class SpriteTests {
        private BufferedImage testSprite;

        @BeforeEach
        void setUpSprite() {
            testSprite = new BufferedImage(
                TEST_SPRITE_SIZE, 
                TEST_SPRITE_SIZE, 
                BufferedImage.TYPE_INT_ARGB
            );
        }

        @Test
        @DisplayName("Should correctly set and get sprite")
        void testSpriteProperty() {
            // When
            tile.setSprite(testSprite);
            
            // Then
            assertSame(testSprite, tile.getSprite(), 
                "Tile sprite should match set sprite");
        }

        @Test
        @DisplayName("Should handle null sprite")
        void testNullSprite() {
            // When
            tile.setSprite(null);
            
            // Then
            assertNull(tile.getSprite(), 
                "Tile should allow null sprite");
        }
    }

    @Test
    @DisplayName("Should correctly set and get tile name")
    void testNameProperty() {
        // Given
        String testName = "Test Tile";
        
        // When
        tile.setName(testName);
        
        // Then
        assertEquals(testName, tile.getName(), 
            "Tile name should match set value");
    }
}