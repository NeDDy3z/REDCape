package org.redcape.view.ui.screens;

import org.redcape.controller.GameState;
import org.redcape.view.ui.BaseUI;
import org.redcape.view.ui.elements.CustomButton;
import org.redcape.view.ui.elements.CustomImage;
import org.redcape.view.ui.elements.CustomLabel;

import javax.swing.*;
import java.awt.*;


/**
 * GameOver class for the game
 * <p>
 * This class represents the game over screen of the game.
 * It is displayed when the player loses the game.
 * </p>
 */
public final class GameOver extends BaseUI {

    // Properties
    /**
     * GameOver screen background image path
     */
    public final String backgroundPath = "src/main/resources/graphics/menu.png";
    public final boolean win;

    // Classes
    public final GameState gameState;

    // UI elements
    private CustomButton menuButton;
    private CustomImage background;
    private CustomLabel title;


    /**
     * Constructor for the GameOver class
     *
     * @param gameState object
     * @param win if true, the player won the game
     */
    public GameOver(GameState gameState, boolean win) {
        this.gameState = gameState;
        this.win = win;

        // Initialize UI components
        build();
        addElements();
        addActionListeners();
    }


    @Override
    public void build() {
        background = new CustomImage("src/main/resources/graphics/menu.png");
        menuButton = new CustomButton("Back to menu");
        title = new CustomLabel("Game Over");

        if (win) {
            title.setText("You win!");
        } else {
            title.setText("You lost :(");
        }

        menuButton.setPosition(0, 500);
        menuButton.setSize(1280, menuButton.getHeight());
        menuButton.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(BaseUI.title);
        title.setPosition(0, 100);
        title.setSize(1280, 50);
        title.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void addElements() {
        setLayout(null); // Use absolute positioning

        add(menuButton);
        add(title);

        revalidate();
        repaint();
    }

    @Override
    public void addActionListeners() {
        menuButton.addActionListener(e -> {
            gameState.setActiveState(GameState.State.MENU);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image - scaled to fit the panel
        background.drawFittedImage(g, this, getWidth(), getHeight());
    }
}
