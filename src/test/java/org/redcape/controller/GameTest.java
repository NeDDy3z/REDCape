package org.redcape.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;


/**
 * GameTest class for testing the Game class.
 * <p>
 * This class contains unit tests for the Game class.
 * </p>
 */
class GameTest {
    private Game game;
    private static final String TEST_LEVEL = "easy";
    private static final long INITIALIZATION_TIMEOUT = 5000; // 5 seconds timeout

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    @Test
    @DisplayName("Game should initialize with all required components")
    void testGameIsInitialized() {
        assertAll("Game initialization components",
            () -> assertNotNull(game, "Game instance should be created"),
            () -> assertNotNull(game.getGameState(), "GameState should be initialized"),
            () -> assertNotNull(game.getLevel(), "Level should be initialized"),
            () -> assertNotNull(game.getPlayer(), "Player should be initialized"),
            () -> assertNotNull(game.getEnemy(), "Enemy should be initialized"),
            () -> assertNotNull(game.getWindow(), "Game window should be initialized"),
            () -> assertNotNull(game.getWindow().getGamePanel(), "Game panel should be initialized")
        );
    }

    @Test
    @DisplayName("Game should properly load selected level")
    void testSetSelectedLevel() {
        // Given
        assertNotEquals(TEST_LEVEL, game.getLevel().getLevelName(), 
            "Initial level should not be the test level");

        // When
        game.setLevelName(TEST_LEVEL);
        game.loadLevel();

        // Then
        assertAll("Level loading",
            () -> assertEquals(TEST_LEVEL, game.getLevel().getLevelName(), 
                "Level name should match selected level"),
            () -> assertNotNull(game.getLevel().getTileMap(),
                "Level map should be loaded"),
            () -> assertTrue(game.getLevel() != null,
                "Level should be fully initialized")
        );
    }

    @Test
    @DisplayName("Game state should transition correctly to running state")
    void testGameStateTransitionToRunning() {
        // Given
        GameState.State initialState = game.getGameState().getActiveState();
        
        // When
        game.getGameState().setActiveState(GameState.State.PLAYING);
        
        // Then
        assertAll("Game state transition",
            () -> assertEquals(GameState.State.PLAYING, 
                game.getGameState().getActiveState(), 
                "Game state should be PLAYING"),
            () -> assertNotEquals(initialState, 
                game.getGameState().getActiveState(), 
                "Game state should have changed from initial state")
        );
    }

    @Test
    @DisplayName("Game window should be properly created with required components")
    void testGameWindowCreation() {
        assertAll("Game window initialization",
            () -> assertNotNull(game.getWindow(), 
                "Game window should be created"),
            () -> assertTrue(game.getWindow() instanceof JFrame, 
                "Game window should be a JFrame instance"),
            () -> assertNotNull(game.getWindow().getGamePanel(), 
                "Game panel should be created"),
            () -> assertTrue(game.getWindow().getGamePanel().isVisible(), 
                "Game panel should be visible")
        );
    }

    @Test
    @DisplayName("Game should initialize within acceptable time")
    void testGameInitializationPerformance() {
        // Given
        long startTime = System.currentTimeMillis();
        
        // When
        Game newGame = new Game();
        long initializationTime = System.currentTimeMillis() - startTime;

        // Then
        assertAll("Game initialization performance",
            () -> assertTrue(initializationTime < INITIALIZATION_TIMEOUT, 
                "Game should initialize within " + INITIALIZATION_TIMEOUT + "ms"),
            () -> assertNotNull(newGame.getGameState(), 
                "GameState should be quickly initialized"),
            () -> assertNotNull(newGame.getWindow(), 
                "Window should be quickly initialized")
        );
    }
}