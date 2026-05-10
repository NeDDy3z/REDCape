package org.redcape.model.object.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redcape.controller.Game;
import org.redcape.model.entity.Player;
import org.redcape.model.object.Item;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


/**
 * Test class for PotionItem.
 * <p>
 *     This class contains unit tests for the PotionItem class, which represents a potion item in the game.
 * </p>
 */
class PotionItemTest {
    private PotionItem potionItem;
    private Player player;
    private static final int TEST_X = 5;
    private static final int TEST_Y = 6;
    private static final String EXPECTED_NAME = "Potion";
    private static final double SPEED_BOOST_AMOUNT = 3.0;
    private static final int BOOST_DURATION = 5;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        potionItem = new PotionItem(TEST_X, TEST_Y);
        player = new Player(game.getWindow().getGamePanel(), game.getKeyController());
    }

    @Nested
    @DisplayName("Initialization Tests")
    class InitializationTests {
        @Test
        @DisplayName("Should initialize with correct properties")
        void testInitialization() {
            assertAll("Potion initialization",
                () -> assertEquals(EXPECTED_NAME, potionItem.getName(), 
                    "Should have correct name"),
                () -> assertEquals(Item.Type.POTION, potionItem.getType(), 
                    "Should have correct type"),
                () -> assertNotNull(potionItem.getImage(), 
                    "Should have an image"),
                () -> assertEquals(TEST_X, potionItem.getWorldX(), 
                    "Should have correct X position"),
                () -> assertEquals(TEST_Y, potionItem.getWorldY(), 
                    "Should have correct Y position")
            );
        }

        @Test
        @DisplayName("Should load image successfully")
        void testImageLoading() {
            BufferedImage img = potionItem.getImage();
            assertAll("Image properties",
                () -> assertNotNull(img, "Image should not be null"),
                () -> assertTrue(img.getWidth() > 0, "Image should have width"),
                () -> assertTrue(img.getHeight() > 0, "Image should have height")
            );
        }
    }

    @Nested
    @DisplayName("Usage Tests")
    class UsageTests {
        @Test
        @DisplayName("Should be usable by default")
        void testCanUse() {
            assertTrue(potionItem.canUse(), 
                "Potion should be usable");
        }

        @Test
        @DisplayName("Should apply speed boost to player")
        void testSpeedBoostEffect() {
            // Given
            double initialSpeed = player.getSpeed();
            assertFalse(player.isSpeedBoostActive(), 
                "Player should not have active speed boost initially");

            // When
            potionItem.use(player);

            // Then
            assertAll("Speed boost effects",
                () -> assertTrue(player.isSpeedBoostActive(), 
                    "Speed boost should be active"),
                () -> assertEquals(initialSpeed + SPEED_BOOST_AMOUNT, 
                    player.getSpeed(), 0.001, 
                    "Player speed should be increased by boost amount"),
                () -> assertTrue(potionItem.isUsed(), 
                    "Potion should be marked as used")
            );
        }

        @Test
        @DisplayName("Should handle null player gracefully")
        void testUseWithoutPlayer() {
            assertDoesNotThrow(() -> potionItem.use(null),
                "Should handle null player without throwing exception");
        }

        @Test
        @DisplayName("Should maintain boost for correct duration")
        void testBoostDuration() throws InterruptedException {
            // Given
            double initialSpeed = player.getSpeed();
            
            // When
            potionItem.use(player);
            
            // Then - immediately after use
            assertTrue(player.isSpeedBoostActive(), 
                "Boost should be active immediately");
            
            // When - after duration
            Thread.sleep((BOOST_DURATION * 1000) + 100);
            player.update();
            
            // Then - after duration
            assertAll("Boost expiration",
                () -> assertFalse(player.isSpeedBoostActive(), 
                    "Boost should expire after duration"),
                () -> assertEquals(initialSpeed, player.getSpeed(), 0.001, 
                    "Speed should return to initial value")
            );
        }
    }
}