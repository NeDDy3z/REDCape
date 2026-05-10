package org.redcape.model.object.items.key;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.redcape.controller.Game;
import org.redcape.model.entity.Player;
import org.redcape.model.object.Inventory;
import org.redcape.model.object.Item;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;


/**
 * KeyItemsTest is a test class for the KeyItem, KeyBladeItem, and KeyRingItem classes.
 * <p>
 * It contains unit tests to verify the functionality of these key item classes, including initialization,
 * usage, and interaction with the player.
 * </p>
 */
class KeyItemsTest {
    private static final int TEST_X = 100;
    private static final int TEST_Y = 200;

    private KeyItem keyItem;
    private KeyBladeItem keyBlade;
    private KeyRingItem keyRing;

    @BeforeEach
    void setUp() {
        keyItem = new KeyItem(TEST_X, TEST_Y);
        keyBlade = new KeyBladeItem(TEST_X, TEST_Y);
        keyRing = new KeyRingItem(TEST_X, TEST_Y);
    }

    @Nested
    @DisplayName("Key Item Tests")
    class KeyItemTests {
        
        @Test
        @DisplayName("Should initialize key with correct properties")
        void testKeyInitialization() {
            assertAll("Key properties",
                () -> assertEquals("Key", keyItem.getName(), 
                    "Should have correct name"),
                () -> assertEquals(Item.Type.KEY, keyItem.getType(), 
                    "Should have KEY type"),
                () -> assertNotNull(keyItem.getImage(), 
                    "Should have an image"),
                () -> assertEquals(TEST_X, keyItem.getWorldX(), 
                    "Should have correct X position"),
                () -> assertEquals(TEST_Y, keyItem.getWorldY(), 
                    "Should have correct Y position"),
                () -> assertFalse(keyItem.isUsed(), 
                    "Should not be used initially"),
                () -> assertFalse(keyItem.isPickedUp(), 
                    "Should not be picked up initially")
            );
        }
    }

    @Nested
    @DisplayName("Key Blade Tests")
    class KeyBladeTests {
        
        @Test
        @DisplayName("Should initialize key blade with correct properties")
        void testKeyBladeInitialization() {
            assertAll("Key blade properties",
                () -> assertEquals("Key Blade", keyBlade.getName(), 
                    "Should have correct name"),
                () -> assertEquals(Item.Type.KEY_PART, keyBlade.getType(), 
                    "Should have KEY_PART type"),
                () -> assertNotNull(keyBlade.getImage(), 
                    "Should have an image"),
                () -> assertEquals(TEST_X, keyBlade.getWorldX(), 
                    "Should have correct X position"),
                () -> assertEquals(TEST_Y, keyBlade.getWorldY(), 
                    "Should have correct Y position")
            );
        }

        @Test
        @DisplayName("Should load correct key blade image")
        void testKeyBladeImage() {
            BufferedImage image = keyBlade.getImage();
            assertAll("Key blade image properties",
                () -> assertNotNull(image, "Image should not be null"),
                () -> assertTrue(image.getWidth() > 0, "Image should have width"),
                () -> assertTrue(image.getHeight() > 0, "Image should have height")
            );
        }
    }

    @Nested
    @DisplayName("Key Ring Tests")
    class KeyRingTests {
        
        @Test
        @DisplayName("Should initialize key ring with correct properties")
        void testKeyRingInitialization() {
            assertAll("Key ring properties",
                () -> assertEquals("Key Ring", keyRing.getName(), 
                    "Should have correct name"),
                () -> assertEquals(Item.Type.KEY_PART, keyRing.getType(), 
                    "Should have KEY_PART type"),
                () -> assertNotNull(keyRing.getImage(), 
                    "Should have an image"),
                () -> assertEquals(TEST_X, keyRing.getWorldX(), 
                    "Should have correct X position"),
                () -> assertEquals(TEST_Y, keyRing.getWorldY(), 
                    "Should have correct Y position")
            );
        }

        @Test
        @DisplayName("Should load correct key ring image")
        void testKeyRingImage() {
            BufferedImage image = keyRing.getImage();
            assertAll("Key ring image properties",
                () -> assertNotNull(image, "Image should not be null"),
                () -> assertTrue(image.getWidth() > 0, "Image should have width"),
                () -> assertTrue(image.getHeight() > 0, "Image should have height")
            );
        }
    }

    @Nested
    @DisplayName("Common Key Items Behavior Tests")
    class CommonBehaviorTests {
        
        @Test
        @DisplayName("Should handle pickup state for all key items")
        void testPickupState() {
            assertAll("Pickup state handling",
                () -> {
                    keyItem.pickUp();
                    assertTrue(keyItem.isPickedUp(), "Key should be picked up");
                },
                () -> {
                    keyBlade.pickUp();
                    assertTrue(keyBlade.isPickedUp(), "Key blade should be picked up");
                },
                () -> {
                    keyRing.pickUp();
                    assertTrue(keyRing.isPickedUp(), "Key ring should be picked up");
                }
            );
        }

        @Test
        @DisplayName("Should allow pickup for all key items by default")
        void testCanPickUp() {
            assertAll("Pickup permission",
                () -> assertTrue(keyItem.canPickUp(), "Key should be pickable"),
                () -> assertTrue(keyBlade.canPickUp(), "Key blade should be pickable"),
                () -> assertTrue(keyRing.canPickUp(), "Key ring should be pickable")
            );
        }

        @Test
        @DisplayName("Should combine key parts into complete key")
        void testKeyCombination() {
            Game game = new Game();  // Assuming there's a Game class

            // Given
            KeyRingItem keyRing = new KeyRingItem(TEST_X, TEST_Y);
            KeyBladeItem keyBlade = new KeyBladeItem(TEST_X, TEST_Y);
            Inventory inventory = new Inventory(new Player(game.getWindow().getGamePanel(), game.getKeyController()));

            // When
            inventory.addItem(keyRing);
            inventory.addItem(keyBlade);

            // Then
            assertAll("Key combination result",
                    () -> assertNull(inventory.getItemByName("Key Ring"),
                            "Key Ring should disappear from inventory"),
                    () -> assertNull(inventory.getItemByName("Key Blade"),
                            "Key Blade should disappear from inventory"),
                    () -> assertNotNull(inventory.getItemByName("Key"),
                            "Complete Key should appear in inventory"),
                    () -> assertTrue(inventory.getItemByName("Key") instanceof KeyItem,
                            "New item should be a KeyItem"),
                    () -> assertEquals(1, inventory.getItems().length,
                            "Inventory should contain exactly one item")
            );
        }
    }

    @Nested
    @DisplayName("Position Handling Tests")
    class PositionTests {
        
        @Test
        @DisplayName("Should update positions correctly for all key items")
        void testPositionUpdate() {
            int newX = 300;
            int newY = 400;
            
            assertAll("Position updates",
                () -> {
                    keyItem.setPosition(newX, newY);
                    assertEquals(newX, keyItem.getWorldX(), "Key X position should update");
                    assertEquals(newY, keyItem.getWorldY(), "Key Y position should update");
                },
                () -> {
                    keyBlade.setPosition(newX, newY);
                    assertEquals(newX, keyBlade.getWorldX(), "Key blade X position should update");
                    assertEquals(newY, keyBlade.getWorldY(), "Key blade Y position should update");
                },
                () -> {
                    keyRing.setPosition(newX, newY);
                    assertEquals(newX, keyRing.getWorldX(), "Key ring X position should update");
                    assertEquals(newY, keyRing.getWorldY(), "Key ring Y position should update");
                }
            );
        }

        @Test
        @DisplayName("Should handle negative positions")
        void testNegativePositions() {
            int newX = -100;
            int newY = -200;
            
            assertAll("Negative position handling",
                () -> {
                    keyItem.setPosition(newX, newY);
                    assertEquals(newX, keyItem.getWorldX(), "Key should handle negative X");
                    assertEquals(newY, keyItem.getWorldY(), "Key should handle negative Y");
                },
                () -> {
                    keyBlade.setPosition(newX, newY);
                    assertEquals(newX, keyBlade.getWorldX(), "Key blade should handle negative X");
                    assertEquals(newY, keyBlade.getWorldY(), "Key blade should handle negative Y");
                },
                () -> {
                    keyRing.setPosition(newX, newY);
                    assertEquals(newX, keyRing.getWorldX(), "Key ring should handle negative X");
                    assertEquals(newY, keyRing.getWorldY(), "Key ring should handle negative Y");
                }
            );
        }
    }
}