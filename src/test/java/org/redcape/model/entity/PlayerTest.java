package org.redcape.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.redcape.controller.Game;
import org.redcape.controller.KeyController;
import org.redcape.model.object.Item;
import org.redcape.model.object.ItemFactory;
import org.redcape.view.GamePanel;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


/**
 * PlayerTest class for testing the Player class.
 * <p>
 * * This class contains unit tests for the Player class.
 * </p>
 */
class PlayerTest {
    private Player player;
    private Game game;
    private GamePanel gamePanel;
    private KeyController keyController;
    private static final double DELTA = 0.001; // For double comparisons

    @BeforeEach
    void setUp() {
        game = new Game();
        gamePanel = game.getWindow().getGamePanel();
        keyController = game.getKeyController();
        player = new Player(gamePanel, keyController);
    }

    @Test
    @DisplayName("Player should be properly initialized with default values")
    void testPlayerInitialization() {
        // Given a newly created player
        // Then all properties should have expected default values
        assertAll("Player initialization",
                () -> assertNotNull(player.getInventory(), "Inventory should not be null"),
                () -> assertEquals(4, player.getSpeed(), "Default speed should be 4"),
                () -> assertEquals(100, player.getMaxHealth(), "Max health should be 100"),
                () -> assertEquals(50, player.getHealth(), "Initial health should be 50"),
                () -> assertFalse(player.isDead(), "Player should not be dead initially")
        );
    }

    @Test
    @DisplayName("Player position should be correctly set relative to tile size")
    void testSetDefaultPosition() {
        // Given
        int tileX = 5;
        int tileY = 7;
        int expectedX = tileX * gamePanel.getTileSize();
        int expectedY = tileY * gamePanel.getTileSize();

        // When
        player.setDefaultPosition(tileX, tileY);

        // Then
        assertAll("Player position",
                () -> assertEquals(expectedX, player.getWorldX(), "World X position should match tile position"),
                () -> assertEquals(expectedY, player.getWorldY(), "World Y position should match tile position")
        );
    }

    @Test
    @DisplayName("Speed boost should properly increase player speed")
    void testActivateSpeedBoost() {
        // Given
        double originalSpeed = player.getSpeed();
        double boostAmount = 2.0;

        // When
        player.activateSpeedBoost(boostAmount, 1);

        // Then
        assertAll("Speed boost activation",
                () -> assertTrue(player.isSpeedBoostActive(), "Speed boost should be active"),
                () -> assertEquals(originalSpeed + boostAmount, player.getSpeed(), DELTA,
                        "Speed should increase by boost amount")
        );
    }

    @Test
    @DisplayName("Speed boost should expire after duration")
    void testSpeedBoostExpires() throws InterruptedException {
        // Given
        double originalSpeed = player.getOriginalSpeed();
        double boostAmount = 2.0;

        // When
        player.activateSpeedBoost(boostAmount, 1);
        Thread.sleep(1100); // Wait for boost to expire
        player.update();

        // Then
        assertAll("Speed boost expiration",
                () -> assertFalse(player.isSpeedBoostActive(), "Speed boost should not be active"),
                () -> assertEquals(originalSpeed, player.getSpeed(), DELTA,
                        "Speed should return to original value")
        );
    }

    @Test
    @DisplayName("Items should be successfully added to player inventory")
    void testInventoryAddItem() {
        // Given
        Item item = ItemFactory.createItem("food", 0, 0);

        // When
        player.getInventory().addItem(item);

        // Then
        assertTrue(Arrays.asList(player.getInventory().getItems()).contains(item),
                "Inventory should contain the added item");
    }
}