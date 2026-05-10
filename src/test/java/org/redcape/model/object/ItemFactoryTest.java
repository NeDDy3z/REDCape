package org.redcape.model.object;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.redcape.model.object.items.FoodItem;
import org.redcape.model.object.items.SuperItem;
import org.redcape.model.object.items.PotionItem;
import org.redcape.model.object.items.CampItem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


/**
 * ItemFactoryTest is a test class for the ItemFactory class.
 * <p>
 * It contains unit tests to verify the functionality of creating different types of items, including food, potions,
 * </p>
 */
class ItemFactoryTest {
    private static final int TEST_X = 10;
    private static final int TEST_Y = 20;

    @Nested
    @DisplayName("Basic Item Creation Tests")
    class BasicItemCreationTests {

        @Test
        @DisplayName("Should create food item with correct properties")
        void testCreateFoodItem() {
            // When
            Item item = ItemFactory.createItem("food", TEST_X, TEST_Y);

            // Then
            assertAll("Food item properties",
                    () -> assertNotNull(item, "Item should not be null"),
                    () -> assertTrue(item instanceof FoodItem, "Should be FoodItem instance"),
                    () -> assertEquals(Item.Type.FOOD, item.getType(), "Should have FOOD type"),
                    () -> assertEquals("Food", item.getName(), "Should have correct name"),
                    () -> assertEquals(TEST_X, ((SuperItem) item).getWorldX(), "Should have correct X position"),
                    () -> assertEquals(TEST_Y, ((SuperItem) item).getWorldY(), "Should have correct Y position"),
                    () -> assertNotNull(item.getImage(), "Should have an image"),
                    () -> assertFalse(((SuperItem) item).isPickedUp(), "Should not be picked up initially")
            );
        }

        @Test
        @DisplayName("Should create potion item with correct properties")
        void testCreatePotionItem() {
            // When
            Item item = ItemFactory.createItem("potion", TEST_X, TEST_Y);

            // Then
            assertAll("Potion item properties",
                    () -> assertNotNull(item, "Item should not be null"),
                    () -> assertTrue(item instanceof PotionItem, "Should be PotionItem instance"),
                    () -> assertEquals(Item.Type.POTION, item.getType(), "Should have POTION type"),
                    () -> assertEquals("Potion", item.getName(), "Should have correct name")
            );
        }

        @Test
        @DisplayName("Should create camp item with correct properties")
        void testCreateCampItem() {
            // When
            Item item = ItemFactory.createItem("camp", TEST_X, TEST_Y);

            // Then
            assertAll("Camp item properties",
                    () -> assertNotNull(item, "Item should not be null"),
                    () -> assertTrue(item instanceof CampItem, "Should be CampItem instance"),
                    () -> assertEquals(Item.Type.CAMP, item.getType(), "Should have CAMP type"),
                    () -> assertEquals("Camp", item.getName(), "Should have correct name")
            );
        }
    }

    @Nested
    @DisplayName("Key Item Creation Tests")
    class KeyItemCreationTests {

        @Test
        @DisplayName("Should create key items with correct properties")
        void testCreateKeyItems() {
            assertAll("Key items creation",
                    () -> {
                        Item keyItem = ItemFactory.createItem("key", TEST_X, TEST_Y);
                        assertEquals(Item.Type.KEY, keyItem.getType(), "Should create key item");
                    },
                    () -> {
                        Item keyRing = ItemFactory.createItem("key ring", TEST_X, TEST_Y);
                        assertEquals(Item.Type.KEY_PART, keyRing.getType(), "Should create key ring");
                    },
                    () -> {
                        Item keyBlade = ItemFactory.createItem("key blade", TEST_X, TEST_Y);
                        assertEquals(Item.Type.KEY_PART, keyBlade.getType(), "Should create key blade");
                    }
            );
        }
    }

    @Nested
    @DisplayName("Special Cases Tests")
    class SpecialCasesTests {

        @Test
        @DisplayName("Should create random item for empty name")
        void testCreateItemWithEmptyName() {
            // When
            Item item = ItemFactory.createItem("", TEST_X, TEST_Y);

            // Then
            assertAll("Random item properties",
                    () -> assertNotNull(item, "Should create a random item"),
                    () -> assertTrue(item instanceof SuperItem, "Should be SuperItem instance"),
                    () -> assertNotNull(item.getType(), "Should have a valid type"),
                    () -> assertNotNull(item.getName(), "Should have a valid name")
            );
        }

        @Test
        @DisplayName("Should create random item for null name")
        void testCreateItemWithNullName() {
            // When
            Item item = ItemFactory.createItem(null, TEST_X, TEST_Y);

            // Then
            assertNotNull(item, "Should create a random item for null name");
        }

        @Test
        @DisplayName("Should create random item for invalid name")
        void testCreateItemWithInvalidName() {
            // When
            Item item = ItemFactory.createItem("invalidName", TEST_X, TEST_Y);

            // Then
            assertNotNull(item, "Should create a random item for invalid name");
        }
    }

    @Nested
    @DisplayName("Picked Up Item Tests")
    class PickedUpItemTests {

        @Test
        @DisplayName("Should create picked up items with correct state")
        void testCreatePickedUpItems() {
            // When
            SuperItem item = ItemFactory.createPickedUpItem("food", TEST_X, TEST_Y);

            // Then
            assertTrue(item.isPickedUp(), "Item should be marked as picked up");
        }

        @Test
        @DisplayName("Should create inactive camp item when picked up")
        void testCreatePickedUpCampItem() {
            // When
            SuperItem campItem = ItemFactory.createPickedUpItem("camp", TEST_X, TEST_Y);

            // Then
            assertAll("Picked up camp item",
                    () -> assertTrue(campItem instanceof CampItem, "Should be CampItem instance"),
                    () -> assertTrue(campItem.isUsed(), "Camp item should be marked as used")
            );
        }
    }

    @Nested
    @DisplayName("Multiple Items Tests")
    class MultipleItemsTests {

        @Test
        @DisplayName("Should create unique instances for multiple items")
        void testCreateMultipleItems() {
            // When
            Item food = ItemFactory.createItem("food", TEST_X, TEST_Y);
            Item potion = ItemFactory.createItem("potion", TEST_X, TEST_Y);

            // Then
            assertAll("Multiple items creation",
                    () -> assertNotNull(food, "Food item should not be null"),
                    () -> assertNotNull(potion, "Potion item should not be null"),
                    () -> assertNotSame(food, potion, "Items should be different instances"),
                    () -> assertNotEquals(food.getType(), potion.getType(), "Items should have different types")
            );
        }

        @Test
        @DisplayName("Should create different random items")
        void testCreateMultipleRandomItems() {
            // When
            List<Item> randomItems = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                randomItems.add(ItemFactory.createItem("", TEST_X, TEST_Y));
            }

            // Then
            assertTrue(randomItems.stream()
                            .map(Item::getType)
                            .distinct()
                            .count() > 1,
                    "Should create different types of random items");
        }
    }
}