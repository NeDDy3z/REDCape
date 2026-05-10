package org.redcape.model.object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redcape.controller.Game;
import org.redcape.model.entity.Player;
import org.redcape.model.object.items.SuperItem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Objects;


/**
 * InventoryTest is a test class for the Inventory class.
 * <p>
 * It contains unit tests to verify the functionality of adding,
 * using, and removing items from the inventory.
 * </p>
 */
class InventoryTest {
    private Inventory inventory;
    private Player player;

    static class TestItem extends SuperItem {
        boolean used = false;
        @Override
        public void use(Player player) {
            used = true;
        }
    }

    @BeforeEach
    void setUp() {
        Game game = new Game();
        player = new Player(game.getWindow().getGamePanel(), game.getKeyController());
        inventory = new Inventory(player);
    }

    @Test
    @DisplayName("Should successfully add item to inventory")
    void testAddItem() {
        // Given
        TestItem item = new TestItem();
        
        // When
        inventory.addItem(item);
        
        // Then
        assertTrue(Arrays.asList(inventory.getItems()).contains(item),
            "Inventory should contain the added item");
    }

    @Test
    @DisplayName("Should not add items when inventory is full")
    void testAddItemWhenFull() {
        // Given
        // Fill inventory to capacity
        for (int i = 0; i < Inventory.SIZE; i++) {
            inventory.addItem(new TestItem());
        }
        TestItem extraItem = new TestItem();
        
        // When
        inventory.addItem(extraItem);
        
        // Then
        assertAll("Full inventory behavior",
            () -> assertFalse(Arrays.asList(inventory.getItems()).contains(extraItem),
                "Extra item should not be added"),
            () -> assertEquals(Inventory.SIZE, Arrays.stream(inventory.getItems())
                .filter(Objects::nonNull).count(),
                "Inventory should maintain its size limit")
        );
    }

    @Test
    @DisplayName("Using item should remove it from inventory and trigger use effect")
    void testUseItemRemovesIt() {
        // Given
        TestItem item = new TestItem();
        inventory.addItem(item);
        
        // When
        inventory.useItem(item);
        
        // Then
        assertAll("Item usage",
            () -> assertFalse(Arrays.asList(inventory.getItems()).contains(item),
                "Item should be removed from inventory"),
            () -> assertTrue(item.used, "Item's use effect should be triggered")
        );
    }

    @Test
    @DisplayName("Should properly remove items from inventory")
    void testRemoveItem() {
        // Given
        TestItem item = new TestItem();
        inventory.addItem(item);
        
        // When
        inventory.useItem(item);
        
        // Then
        assertNull(Arrays.stream(inventory.getItems())
            .filter(i -> i == item)
            .findFirst()
            .orElse(null),
            "Item should be completely removed from inventory");
    }
}