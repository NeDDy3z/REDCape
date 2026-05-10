package org.redcape.view.ui;

import lombok.extern.java.Log;
import org.redcape.controller.KeyController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;


/**
 * UIController class for the game
 * <p>
 * This class is responsible for managing the UI components of the game.
 * It handles the rendering of different screens and manages the key events.
 * </p>
 */
@Log
public final class UIController {
    // View components
    private final Map<String, BaseUI> screens = new HashMap<>();
    ;
    private BaseUI activeScreen;

    // Classes
    private final JFrame frame;
    private final KeyController keyController;


    /**
     * Constructor
     *
     * @param frame         object
     * @param keyController object
     */
    public UIController(JFrame frame, KeyController keyController) {
        this.frame = frame;
        this.keyController = keyController;

        frame.setBackground(Color.BLACK);
    }

    /**
     * Set the screen supposed to be rendered
     *
     * @param screen object
     */
    public void setActiveScreen(BaseUI screen) {
        // Remove old screen and replace with new one
        if (activeScreen != null) {
            activeScreen.setVisible(false);
            activeScreen.removeKeyListener(keyController);
            frame.remove(activeScreen);
        }
        activeScreen = screen;
        activeScreen.setVisible(true);

        // Add the active screen
        frame.add(activeScreen);
        activeScreen.setFocusable(true);
        activeScreen.requestFocusInWindow();
        activeScreen.addKeyListener(keyController);

        // Ensure the frame updates
        frame.revalidate();
        frame.repaint();
    }


    /**
     * Change the active screen
     *
     * @param name of the screen to be set
     */
    public void setActiveScreen(String name) {
        if (screens.containsKey(name)) {
            setActiveScreen(screens.get(name));
        } else {
            System.out.println("Screen not found: " + name);
        }
    }

    /**
     * Adds a screen to the UI controller
     *
     * @param name   of the screen
     * @param screen object
     */
    public void addScreen(String name, BaseUI screen) {
        screens.put(name, screen);
    }

    /**
     * Re-render screen
     */
    public void repaint() {
        if (activeScreen != null) {
            activeScreen.repaint();
        }
    }
}