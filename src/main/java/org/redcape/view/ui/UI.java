package org.redcape.view.ui;

/**
 * UI interface for the game.
 * <p>
 * This interface defines the methods that should be implemented by any UI class.
 * It provides a structure for building, adding elements, and adding action listeners to the UI.
 * </p>
 */
public interface UI {
    /**
     * Builds the UI components.
     * Set the layout, size, and other properties of the UI elements
     */
    void build();

    /**
     * Adds UI elements to the panel.
     * This method should be called after the build method to add components to the UI.
     */
    void addElements();

    /**
     * Adds action listeners to the UI elements.
     * This method should be called after the addElements method to handle user interactions.
     */
    void addActionListeners();
}
