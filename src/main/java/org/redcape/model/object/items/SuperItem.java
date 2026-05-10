package org.redcape.model.object.items;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.redcape.model.entity.Player;
import org.redcape.model.object.Item;
import org.redcape.view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * SuperItem class representing an item in the game.
 * <p>
 * This class implements the Item interface and provides the basic structure and behavior of an item in the game.
 * </p>
 */
@Getter
@Setter
@Log
public class SuperItem implements Item {

    // Parameters
    private String name;
    private BufferedImage image;
    private Item.Type type;

    public boolean used = false;
    public boolean pickedUp = false;

    private int worldX, worldY;

    // Collision
    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48); // Also known as solidArea
    private boolean collision = false;
    public int collisionAreaDefaultX = 10, collisionAreaDefaultY = 10;


    /**
     * Draw the item on the screen
     *
     * @param g2d object
     * @param gamePanel object
     */
    public void draw(Graphics2D g2d, GamePanel gamePanel) {
        // Helper variables
        int tileSize = gamePanel.getTileSize();

        int playerWorldX = gamePanel.getPlayer().getWorldX();
        int playerWorldY = gamePanel.getPlayer().getWorldY();
        int playerScreenX = gamePanel.getPlayer().getScreenX();
        int playerScreenY = gamePanel.getPlayer().getScreenY();

        int screenX = worldX - playerWorldX + playerScreenX;
        int screenY = worldY - playerWorldY + playerScreenY;

        // Draw an item in its designated world position
        if (
                worldX + tileSize > playerWorldX - playerScreenX &&
                        worldX - tileSize < playerWorldX + playerScreenX &&
                        worldY + tileSize > playerWorldY - playerScreenY &&
                        worldY - tileSize < playerWorldY + playerScreenY
        ) {
            g2d.drawImage(image, screenX, screenY, tileSize, tileSize, null);
        }
    }

    /**
     * Draw hitboxes for debugging purposes
     *
     * @param g2d object
     * @param gamePanel observer
     */
    public void drawHitBox(Graphics2D g2d, GamePanel gamePanel) {
        // Helper variables
        int playerWorldX = gamePanel.getPlayer().getWorldX();
        int playerWorldY = gamePanel.getPlayer().getWorldY();
        int playerScreenX = gamePanel.getPlayer().getScreenX();
        int playerScreenY = gamePanel.getPlayer().getScreenY();

        int screenX = worldX - playerWorldX + playerScreenX;
        int screenY = worldY - playerWorldY + playerScreenY;

        g2d.setColor(Color.RED);
        g2d.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
    }


    /**
     * Set the position of the item
     *
     * @param x positon
     * @param y position
     */
    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void use() {
        used = true;
        log.info("Item used: " + getName());
    }

    @Override
    public void use(Player player) {
        log.info("Item used: " + getName());
    }

    @Override
    public boolean canUse() {
        return !used;
    }


    @Override
    public void pickUp() {
        pickedUp = true;
    }

    @Override
    public boolean canPickUp() {
        return !pickedUp;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

}
