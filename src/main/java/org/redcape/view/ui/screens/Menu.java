package org.redcape.view.ui.screens;

import lombok.extern.java.Log;

import org.redcape.controller.GameState;
import org.redcape.view.ui.BaseUI;
import org.redcape.view.ui.elements.CustomButton;
import org.redcape.view.ui.elements.CustomImage;
import org.redcape.view.ui.elements.CustomLabel;

import java.awt.*;


/**
 * Menu class for the game
 * <p>
 * This class is responsible for displaying the main menu of the game.
 * It includes buttons for starting a new game, continuing an existing game, and exiting the game.
 * </p>
 */
@Log
public final class Menu extends BaseUI {

    // Properties
    public final String backgroundPath = "src/main/resources/graphics/menu.png";

    // Game components
    private final GameState gameState;

    // UI components
    private CustomButton continueButton;
    private CustomButton exitButton;
    private CustomButton startButton;
    private CustomImage background;
    private CustomLabel title;


    /**
     * Constructor
     * @param gameState object
     */
    public Menu(GameState gameState) {
        this.gameState = gameState;

        build();
        addElements();
        addActionListeners();
    }

    @Override
    public void build() {
        background = new CustomImage(backgroundPath);
        continueButton = new CustomButton("Continue");
        exitButton = new CustomButton("Exit");
        startButton = new CustomButton("Start new game");
        title = new CustomLabel("RED:Cape");

        continueButton.setPosition(100, 120);
        exitButton.setPosition(100, 200);
        startButton.setPosition(100, 160);
        startButton.setSize(400, startButton.getHeight());
        title.setFont(BaseUI.title);
        title.setPosition(100, 50);
        title.setSize(500, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image - scaled to fit the panel
        background.drawFittedImage(g, this, getWidth(), getHeight());
    }

    @Override
    public void addElements() {
        setLayout(null); // Use absolute positioning

        add(continueButton);
        add(exitButton);
        add(startButton);
        add(title);

        revalidate();
        repaint();
    }

    @Override
    public void addActionListeners() {
        continueButton.addActionListener(e -> {
            log.info(startButton.getText() + " button clicked");
            gameState.setActiveState(GameState.State.CONTINUE);
        });


        startButton.addActionListener(e -> {
            log.info(startButton.getText() + " button clicked");
            gameState.setActiveState(GameState.State.NEW_GAME);
        });

        exitButton.addActionListener(e -> {
            log.info(exitButton.getText() + " button clicked");
            System.exit(0);
        });
    }
}