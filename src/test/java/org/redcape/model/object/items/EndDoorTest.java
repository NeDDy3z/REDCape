package org.redcape.model.object.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.redcape.model.object.Item;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;


/**
 * EndDoorTest is a test class for the EndDoor class.
 * <p>
 * It contains unit tests to verify the functionality of the EndDoor class, including initialization, usage,
 * </p>
 */
class EndDoorTest {
    private EndDoor endDoor;
    private static final int TEST_X = 100;
    private static final int TEST_Y = 200;
    private static final String EXPECTED_NAME = "End Door";

    @BeforeEach
    void setUp() {
        endDoor = new EndDoor(TEST_X, TEST_Y);
    }

    @Nested
    @DisplayName("Initialization Tests")
    class InitializationTests {

        @Test
        @DisplayName("Should initialize with correct properties")
        void testInitialization() {
            assertAll("End door initialization",
                    () -> assertEquals(EXPECTED_NAME, endDoor.getName(),
                            "Should have correct name"),
                    () -> assertEquals(Item.Type.END_DOOR, endDoor.getType(),
                            "Should have END_DOOR type"),
                    () -> assertEquals(TEST_X, endDoor.getWorldX(),
                            "Should have correct X position"),
                    () -> assertEquals(TEST_Y, endDoor.getWorldY(),
                            "Should have correct Y position"),
                    () -> assertNotNull(endDoor.getImage(),
                            "Should have an image loaded"),
                    () -> assertFalse(endDoor.isUsed(),
                            "Should not be used initially")
            );
        }

        @Test
        @DisplayName("Should load correct image")
        void testImageLoading() {
            // When
            BufferedImage image = endDoor.getImage();

            // Then
            assertAll("Image properties",
                    () -> assertNotNull(image, "Image should not be null"),
                    () -> assertTrue(image.getWidth() > 0, "Image should have width"),
                    () -> assertTrue(image.getHeight() > 0, "Image should have height")
            );
        }
    }

    @Nested
    @DisplayName("Usage Tests")
    class UsageTests {

        @Test
        @DisplayName("Should be usable by default")
        void testCanUse() {
            assertTrue(endDoor.canUse(),
                    "End door should be usable by default");
        }

        @Test
        @DisplayName("Should be pickable by default")
        void testCanPickUp() {
            assertTrue(endDoor.canPickUp(),
                    "End door should be pickable by default");
        }

        @Test
        @DisplayName("Should track usage state")
        void testUsageState() {
            // When
            endDoor.use();

            // Then
            assertFalse(endDoor.isUsed(),
                    "Usage state should not change after use() call");
        }
    }

    @Nested
    @DisplayName("Position Tests")
    class PositionTests {

        @Test
        @DisplayName("Should update position correctly")
        void testPositionUpdate() {
            // Given
            int newX = 300;
            int newY = 400;

            // When
            endDoor.setPosition(newX, newY);

            // Then
            assertAll("Position update",
                    () -> assertEquals(newX, endDoor.getWorldX(),
                            "Should update X position"),
                    () -> assertEquals(newY, endDoor.getWorldY(),
                            "Should update Y position")
            );
        }
    }
}