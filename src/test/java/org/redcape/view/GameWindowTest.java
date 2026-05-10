package org.redcape.view;

import org.junit.jupiter.api.*;
import org.redcape.controller.Game;
import org.redcape.controller.GameState;
import org.redcape.controller.KeyController;
import org.redcape.model.level.Level;
import org.redcape.model.level.LevelFactory;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * GameWindowTest class for testing the game render window
 * <p>
 * This class contains unit tests for the GameWindow class.
 * </p>
 */
class GameWindowTest {
    private GameWindow gameWindow;
    private static final int EXPECTED_WIDTH = 800;
    private static final int EXPECTED_HEIGHT = 600;
    private static final String WINDOW_TITLE = "RED:Cape";


    @BeforeEach
    void setUp() {
        Game game = new Game();
        gameWindow = new GameWindow(game.getKeyController(), game.getLevel(), game.getGameState());
    }

    @AfterEach
    void tearDown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            if (gameWindow != null) {
                gameWindow.dispose();
            }
        });
    }


    @Test
    @DisplayName("Window should open correctly with proper visibility")
    void testOpenWindow() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // When
            gameWindow.open();

            // Then
            assertAll("Window opening state",
                    () -> assertTrue(gameWindow.isVisible(),
                            "Window should be visible after opening"),
                    () -> assertTrue(gameWindow.isDisplayable(),
                            "Window should be displayable"),
                    () -> assertFalse(gameWindow.isResizable(),
                            "Window should not be resizable by default")
            );
        });
    }

    @Test
    @DisplayName("Window should have correct title and properties")
    void testWindowProperties() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // When
            gameWindow.open();

            // Then
            assertAll("Window properties",
                    () -> assertEquals(WINDOW_TITLE, gameWindow.getTitle(),
                            "Window should have correct title"),
                    () -> assertNotNull(gameWindow.getIconImage(),
                            "Window should have an icon"),
                    () -> assertEquals(JFrame.EXIT_ON_CLOSE, gameWindow.getDefaultCloseOperation(),
                            "Window should close on exit")
            );
        });
    }

    @Test
    @DisplayName("Window should contain required game components")
    void testGameComponents() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // When
            gameWindow.open();

            // Then
            assertAll("Game components",
                    () -> assertNotNull(gameWindow.getGamePanel(),
                            "GamePanel should be initialized"),
                    () -> assertTrue(gameWindow.getGamePanel().isVisible(),
                            "GamePanel should be visible"),
                    () -> assertTrue(gameWindow.getComponents().length > 0,
                            "Window should contain components")
            );
        });
    }

    @Test
    @DisplayName("Window should handle focus properly")
    void testWindowFocus() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // When
            gameWindow.open();
            gameWindow.requestFocus();

            // Then
            assertAll("Window focus handling",
                    () -> assertTrue(gameWindow.isFocusable(),
                            "Window should be focusable"),
                    () -> assertTrue(gameWindow.getGamePanel().isFocusable(),
                            "GamePanel should be focusable")
            );
        });
    }

    @Test
    @DisplayName("Window should dispose resources properly")
    void testWindowDisposal() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Given
            gameWindow.open();

            // When
            gameWindow.dispose();

            // Then
            assertAll("Window disposal",
                    () -> assertFalse(gameWindow.isVisible(),
                            "Window should not be visible after disposal"),
                    () -> assertFalse(gameWindow.isDisplayable(),
                            "Window should not be displayable after disposal")
            );
        });
    }
}
