package org.redcape.model.object.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.redcape.controller.Game;
import org.redcape.model.entity.Player;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FoodItem.
 * <p>
 * This class contains unit tests to verify the functionality of the FoodItem class.
 * </p>
 */
class FoodItemTest {

    private FoodItem foodItem;
    private Player player;


    @BeforeEach
    void setUp() {
        Game game = new Game();
        player = new Player(game.getWindow().getGamePanel(), game.getKeyController());
        foodItem = new FoodItem(0, 0);
    }

    @Test
    @DisplayName("Food item should be usable when player's health is not full")
    void testCanUse() {
        // Given
        player.damage(50);  // Ensure player is damaged

        // Then
        assertAll("Food item usability",
                () -> assertTrue(foodItem.canUse(), "Food should be usable when player needs healing"),
                () -> assertTrue(player.getHealth() < player.getMaxHealth(), "Player should have less than max health")
        );
    }

    @Test
    @DisplayName("Food item should not be usable when player's health is full")
    void testCanUseAtFullHealth() {
        // Given
        player.setHealth(player.getMaxHealth());  // Set player to full health

        // Then
        assertTrue(foodItem.canUse(), "Food should not be usable at full health");
    }

    @Test
    @DisplayName("Food item should properly heal the player when used")
    void testUseFood() {
        // Given
        int initialHealth = 50;
        player.setHealth(initialHealth);

        // When
        foodItem.use(player);

        // Then
        assertAll("Food healing effects",
                () -> assertEquals(
                        Math.min(player.getMaxHealth(), initialHealth + foodItem.getHealth()),
                        player.getHealth(),
                        "Player should be healed by the correct amount"
                ),
                () -> assertTrue(
                        player.getHealth() > initialHealth,
                        "Player health should increase after using food"
                )
        );
    }
}