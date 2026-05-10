package org.redcape.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.redcape.controller.Game;
import org.redcape.controller.KeyController;
import org.redcape.view.GamePanel;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test class for the Enemy class.
 * <p>
 * This class contains unit tests for the Enemy class, testing its behavior and interactions with the Player class.
 * </p>
 */
class EnemyTest {
    private Enemy enemy;
    private Player player;
    private GamePanel gamePanel;
    private Game game;
    private KeyController keyController;
    private static final double DELTA = 0.001;

    @BeforeEach
    void setUp() {
        game = new Game();
        gamePanel = game.getWindow().getGamePanel();
        keyController = game.getKeyController();
        player = new Player(gamePanel, keyController);
        gamePanel.setPlayer(player);
        enemy = new Enemy(gamePanel);
    }

    @Test
    @DisplayName("Enemy should be properly initialized with default values")
    void testEnemyInitialization() {
        assertAll("Enemy initialization",
            () -> assertEquals(30, enemy.getHealth(), "Initial health should be 30"),
            () -> assertEquals(30, enemy.getMaxHealth(), "Max health should be 30"),
            () -> assertEquals(3, enemy.getSpeed(), "Speed should be 3"),
            () -> assertFalse(enemy.isDead(), "Enemy should not be dead initially"),
            () -> assertNotNull(enemy.getGamePanel(), "GamePanel should be set")
        );
    }

    @Test
    @DisplayName("Enemy should move towards player when in chase range")
    void testEnemyChasePlayer() {
        // Given
        double playerX = 100;
        double playerY = 100;
        double enemyX = 50;
        double enemyY = 50;
        
        player.setWorldX((int) playerX);
        player.setWorldY((int) playerY);
        enemy.setWorldX((int) enemyX);
        enemy.setWorldY((int) enemyY);

        // When
        double initialDistance = calculateDistance(
            enemy.getWorldX(), enemy.getWorldY(),
            player.getWorldX(), player.getWorldY()
        );
        enemy.update();
        double newDistance = calculateDistance(
            enemy.getWorldX(), enemy.getWorldY(),
            player.getWorldX(), player.getWorldY()
        );

        // Then
        assertTrue(newDistance < initialDistance,
            "Enemy should move closer to player when chasing");
    }

    @Test
    @DisplayName("Enemy should wander when player is far away")
    void testEnemyWanderWhenFar() {
        // Given
        player.setWorldX(1000);
        player.setWorldY(1000);
        enemy.setWorldX(0);
        enemy.setWorldY(0);
        
        double initialX = enemy.getWorldX();
        double initialY = enemy.getWorldY();

        // When
        enemy.update();

        // Then
        assertAll("Enemy wandering behavior",
            () -> assertTrue(
                enemy.getWorldX() != initialX || enemy.getWorldY() != initialY,
                "Enemy should change position when wandering"
            ),
            () -> assertTrue(
                Math.abs(enemy.getWorldX() - initialX) <= enemy.getSpeed() &&
                Math.abs(enemy.getWorldY() - initialY) <= enemy.getSpeed(),
                "Enemy movement should be within speed limits"
            )
        );
    }

    @Test
    @DisplayName("Enemy should damage player on collision")
    void testEnemyDamagesPlayerOnCollision() {
        // Given
        int initialPlayerHealth = player.getHealth();
        player.setWorldX(200);
        player.setWorldY(200);
        enemy.setWorldX(200);
        enemy.setWorldY(200);

        // When
        enemy.update();

        // Then
        assertAll("Player damage on collision",
            () -> assertTrue(
                player.getHealth() < initialPlayerHealth,
                "Player should lose health on enemy collision"
            ),
            () -> assertTrue(
                initialPlayerHealth - player.getHealth() > 0,
                "Damage dealt should be positive"
            )
        );
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}