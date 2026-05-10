package org.redcape.view.ui.screens;

import org.redcape.model.entity.Player;
import org.redcape.model.object.Inventory;
import org.redcape.view.ui.BaseUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * GUI class for the game
 * <p>
 * This class is responsible for rendering the game UI.
 * It extends the BaseUI class to provide a consistent look and feel.
 * </p>
 */
public final class GUI extends BaseUI {

    private final int inventorySlotSize = 48;
    private String message;
    private long messageEndTime;

    private Player player;

    /**
     * Constructor for the GUI class
     *
     * @param player object
     */
    public GUI(Player player) {
        this.player = player;
    }


    /**
     * Draws the GUI
     *
     * @param g2d object
     */
    public void draw(Graphics2D g2d) {
        drawInventory(g2d);
        drawHealth(g2d);
        drawPotion(g2d);

        // Draw a message if active
        if (message != null && System.currentTimeMillis() < messageEndTime) {
            g2d.setColor(healthColor);
            g2d.setFont(text);
            g2d.drawString(message, 100, 100);
        }
    }

    /**
     * Show a message on the screen
     * <p>
     * This method displays a message on the screen for a short duration.
     * </p>
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        this.message = message;
        this.messageEndTime = System.currentTimeMillis() + 5000; // show for 5 seconds
    }


    /**
     * Draws the health bar
     * <p>
     * This method draws a health bar on the screen.
     * The health bar is filled based on the player's current health.
     * </p>
     *
     * @param g2d graphics object
     */
    private void drawHealth(Graphics2D g2d) {
        int inventoryOffset = Inventory.SIZE * inventorySlotSize;
        double sizeMultiplier = 1.5;

        // Health status
        g2d.setColor(healthBackgroundColor);
        g2d.fillRect(13, inventoryOffset + 20 + 5, 10, (int) (player.getMaxHealth() * sizeMultiplier));
        g2d.setColor(healthColor);
        g2d.fillRect(13, inventoryOffset + 20 + 5, 10, (int) (sizeMultiplier * (100 * (((float) player.getHealth() / (float) player.getMaxHealth())))));

        // Borders
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(8, inventoryOffset + 20, 20, 5);
        g2d.fillRect(8, inventoryOffset + 20 + 5 + (int) (sizeMultiplier * player.getMaxHealth()), 20, 5);
    }

    /**
     * Draws the potion effect bar
     * <p>
     * This method draws a potion effect bar on the screen.
     * The potion effect bar is filled based on the player's current potion effect.
     * </p>
     *
     * @param g2d graphics object
     */
    private void drawPotion(Graphics2D g2d) {
        int inventoryOffset = Inventory.SIZE * inventorySlotSize;
        double sizeMultiplier = 1.5;
        int barHeight = (int) (player.getPotionEffectMax() * sizeMultiplier);

        // Potion
        g2d.setColor(potionackgroundColor);
        g2d.fillRect(45, inventoryOffset + 20 + 5, 10, barHeight);

        if (player.isSpeedBoostActive()) {
            long remainingMillis = player.getSpeedBoostEndTime() - System.currentTimeMillis();
            int totalDuration = 5; // seconds, or use the actual duration if variable
            double percent = Math.max(0, remainingMillis / 1000.0 / totalDuration);
            int fillHeight = (int) (barHeight * percent);

            g2d.setColor(potionColor);
            g2d.fillRect(
                    45,
                    inventoryOffset + 20 + 5, // always start at the top
                    10,
                    fillHeight // height decreases as time runs out
            );

            // Draw remaining time as text
            int remainingSeconds = (int) Math.ceil(remainingMillis / 1000.0);
        } else {
            // Normal potion effect bar
            g2d.setColor(potionackgroundColor);
            g2d.fillRect(45, inventoryOffset + 20 + 5, 10, (int) (sizeMultiplier * (100 * (((float) player.getPotionEffect() / (float) player.getPotionEffectMax())))));
        }

        // Borders
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(inventorySlotSize - 8, inventoryOffset + 20, 20, 5);
        g2d.fillRect(inventorySlotSize - 8, inventoryOffset + 20 + 5 + (int) (sizeMultiplier * player.getMaxHealth()), 20, 5);
    }

    /**
     * Draw the whole inventory
     *
     * @param g2d graphics object
     */
    private void drawInventory(Graphics2D g2d) {
        drawSlots(g2d);
        drawItems(g2d);
    }

    /**
     * Draws the item slots
     * Can be replaced with an image later
     *
     * @param g2d graphics object
     */
    private void drawSlots(Graphics2D g2d) {
        for (int i = 0; i < Inventory.SIZE; i++) {
            Rectangle2D slot = new Rectangle2D.Double(10, 10 + i * inventorySlotSize, inventorySlotSize, inventorySlotSize);

            // Fill the rectangle with a background color
            g2d.setColor(Color.DARK_GRAY);
            g2d.fill(slot);

            // Draw the rectangle border
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(4));
            g2d.draw(slot);
        }
    }

    /**
     * Draws the items in the inventory
     *
     * @param g2d graphics
     */
    private void drawItems(Graphics2D g2d) {
        for (int i = 0; i < Inventory.SIZE; i++) {
            if (player.getInventory().getItems()[i] != null) {
                g2d.drawImage(player.getInventory().getItems()[i].getImage(), 10, 10 + i * inventorySlotSize, inventorySlotSize, inventorySlotSize, null);
            }
        }
    }


    @Override
    public void build() {
    }

    @Override
    public void addElements() {
    }

    @Override
    public void addActionListeners() {
    }
}
