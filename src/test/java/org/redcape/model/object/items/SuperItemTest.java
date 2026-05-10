package org.redcape.model.object.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.redcape.model.entity.Player;
import org.redcape.model.object.Item;

import static org.junit.jupiter.api.Assertions.*;

class SuperItemTest {
    private TestSuperItem superItem;
    private static final int TEST_X = 1;
    private static final int TEST_Y = 2;
    private static final String TEST_NAME = "TestSuper";

    // Test implementation of SuperItem for testing
    private static class TestSuperItem extends SuperItem {
        public void use(Player player) {
            setUsed(true);
        }
    }

    @BeforeEach
    void setUp() {
        superItem = new TestSuperItem();
    }

    @Nested
    @DisplayName("Position Tests")
    class PositionTests {
        @Test
        @DisplayName("Should correctly set and get position")
        void testPosition() {
            // When
            superItem.setPosition(TEST_X, TEST_Y);

            // Then
            assertAll("Position coordinates",
                () -> assertEquals(TEST_X, superItem.getWorldX(), 
                    "X coordinate should match set value"),
                () -> assertEquals(TEST_Y, superItem.getWorldY(), 
                    "Y coordinate should match set value")
            );
        }

        @Test
        @DisplayName("Should handle negative position values")
        void testNegativePosition() {
            // When
            superItem.setPosition(-TEST_X, -TEST_Y);

            // Then
            assertAll("Negative position coordinates",
                () -> assertEquals(-TEST_X, superItem.getWorldX(), 
                    "Negative X coordinate should be handled"),
                () -> assertEquals(-TEST_Y, superItem.getWorldY(), 
                    "Negative Y coordinate should be handled")
            );
        }
    }

    @Nested
    @DisplayName("Property Tests")
    class PropertyTests {
        @Test
        @DisplayName("Should correctly set and get name")
        void testName() {
            // When
            superItem.setName(TEST_NAME);

            // Then
            assertEquals(TEST_NAME, superItem.getName(), 
                "Item name should match set value");
        }

        @Test
        @DisplayName("Should correctly set and get type")
        void testType() {
            // When
            superItem.setType(Item.Type.POTION);

            // Then
            assertEquals(Item.Type.POTION, superItem.getType(), 
                "Item type should match set value");
        }
    }

    @Nested
    @DisplayName("Usage Tests")
    class UsageTests {
        @Test
        @DisplayName("Should be usable by default")
        void testDefaultCanUse() {
            assertTrue(superItem.canUse(), 
                "SuperItem should be usable by default");
        }

        @Test
        @DisplayName("Should track usage state")
        void testUsageState() {
            // Given
            assertFalse(superItem.isUsed(), "Item should start as unused");

            // When
            superItem.use(null);

            // Then
            assertTrue(superItem.isUsed(), "Item should be marked as used");
        }
    }
}