package org.redcape.view.ui.screens;

import lombok.extern.java.Log;

import org.redcape.controller.GameState;
import org.redcape.view.ui.BaseUI;
import org.redcape.view.ui.elements.CustomButton;
import org.redcape.view.ui.elements.CustomImage;
import org.redcape.view.ui.elements.CustomLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Paused class for the game
 * <p>
 * This class represents the paused screen of the game.
 * It is displayed when the game is paused.
 * </p>
 */
@Log
public final class Paused extends BaseUI {

    // Properties
    public final String backgroundPath = "src/main/resources/graphics/menu.png";

    // Game components
    private final GameState gameState;

    // UI components
    private CustomImage background;
    private CustomLabel title;
    private CustomButton resumeButton;
    private CustomButton menuButton;

    public Paused(GameState gameState) {
        this.gameState = gameState;

        build();
        addElements();
        addActionListeners();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image - scaled to fit the panel
        background.drawFittedImage(g, this, getWidth(), getHeight());

        // Add dark tint
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void build() {
        background = new CustomImage(backgroundPath);

        title = new CustomLabel("Paused");
        resumeButton = new CustomButton("Resume");
        menuButton = new CustomButton("Menu");

        title.setFont(BaseUI.title);
        title.setPosition(100, 50);
        title.setSize(500, 50);
        resumeButton.setPosition(100, 120);
        menuButton.setPosition(100, 160);
    }

    @Override
    public void addElements() {
        setLayout(null); // Use absolute positioning
        add(title);
        add(resumeButton);
        add(menuButton);

        revalidate();
        repaint();
    }

    @Override
    public void addActionListeners() {
        resumeButton.addActionListener(e -> {
            log.info(resumeButton.getText() + " button clicked");
            gameState.setActiveState(GameState.State.PLAYING);
        });

        menuButton.addActionListener(e -> {
            log.info(menuButton.getText() + " button clicked");
            gameState.setActiveState(GameState.State.MENU);
        });
    }
}
