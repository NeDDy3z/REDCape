package org.redcape.controller;

import lombok.extern.java.Log;
import org.redcape.model.tile.Tile;
import org.redcape.model.entity.Entity;
import org.redcape.view.GamePanel;
import org.redcape.model.object.items.SuperItem;

import java.awt.*;
import java.util.ArrayList;


/**
 * CollisionController class for handling collisions in the game
 * <p>
 * This class is responsible for checking collisions between entities and objects,
 * as well as between entities and tiles.
 * </p>
 */
@Log
public final class CollisionController {

    private final GamePanel gamePanel;

    /**
     * Constructor for the CollisionController class
     *
     * @param gamePanel object
     */
    public CollisionController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    /**
     * Check entity collision with objects, returns the index of the object collided with.
     *
     * @param entity object
     * @param player boolean to check if the entity is a player
     * @return the index of the object that was collided with, or 999 if no collision occurred
     */
    public int checkObjectCollision(Entity entity, boolean player) {
        int index = 999;

        ArrayList<SuperItem> objects = gamePanel.getLevel().getLevelObjects();

        for (int i = 0; i < objects.size(); i++) {
            SuperItem object = objects.get(i);
            if (object != null) {
                entity.collisionArea.x += entity.getWorldX();
                entity.collisionArea.y += entity.getWorldY();
                object.collisionArea.x += object.getWorldX();
                object.collisionArea.y += object.getWorldY();

                switch (entity.getDirection()) {
                    case "up":
                        entity.collisionArea.y -= (int) entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionUp(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.collisionArea.y += (int) entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionDown(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.collisionArea.x -= (int) entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionLeft(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.collisionArea.x += (int) entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionRight(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }

                // Reset position
                entity.collisionArea.x = entity.getCollisionAreaDefaultX();
                entity.collisionArea.y = entity.getCollisionAreaDefaultY();
                object.collisionArea.x = object.getCollisionAreaDefaultX();
                object.collisionArea.y = object.getCollisionAreaDefaultY();

            }
        }

        return index;
    }

    /**
     * Checks if an entity (player) collides with a tile
     * Prevents walking on the "unwalkable" tiles
     *
     * @param entity an entity being checked for collision
     */
    public void checkTileCollision(Entity entity) {
        // Declare Helper vars like player collArea, pos, etc...
        int worldX = entity.getWorldX();
        int worldY = entity.getWorldY();
        Rectangle collisionArea = entity.getCollisionArea();
        int tileSize = gamePanel.getTileSize();

        int entityLeftX = worldX + collisionArea.x;
        int entityRightX = worldX + collisionArea.x + collisionArea.width;
        int entityTopY = worldY + collisionArea.y;
        int entityBottomY = worldY + collisionArea.y + collisionArea.height;

        int entityLeftCol = entityLeftX / tileSize;
        int entityRightCol = entityRightX / tileSize;
        int entityTopRow = entityTopY / tileSize;
        int entityBottomRow = entityBottomY / tileSize;

        Tile[][] tiles = gamePanel.getTileManager().getTiles();
        Tile tileA, tileB;
        int entitySpeed = (int) entity.getSpeed();

        // Reset collision flags
        boolean collisionUp = false, collisionDown = false, collisionLeft = false, collisionRight = false;

        // Check for collision directions
        // Top
        if ((entityTopY - entitySpeed) / tileSize >= 0) {
            tileA = tiles[entityLeftCol][(entityTopY - entitySpeed) / tileSize];
            tileB = tiles[entityRightCol][(entityTopY - entitySpeed) / tileSize];
            collisionUp = tileA.isCollision() || tileB.isCollision();
        }
        // Down
        if ((entityBottomY + entitySpeed) / tileSize < tiles[0].length) {
            tileA = tiles[entityLeftCol][(entityBottomY + entitySpeed) / tileSize];
            tileB = tiles[entityRightCol][(entityBottomY + entitySpeed) / tileSize];
            collisionDown = tileA.isCollision() || tileB.isCollision();
        }
        // Left
        if ((entityLeftX - entitySpeed) / tileSize >= 0) {
            tileA = tiles[(entityLeftX - entitySpeed) / tileSize][entityTopRow];
            tileB = tiles[(entityLeftX - entitySpeed) / tileSize][entityBottomRow];
            collisionLeft = tileA.isCollision() || tileB.isCollision();
        }
        // Right
        if ((entityRightX + entitySpeed) / tileSize < tiles.length) {
            tileA = tiles[(entityRightX + entitySpeed) / tileSize][entityTopRow];
            tileB = tiles[(entityRightX + entitySpeed) / tileSize][entityBottomRow];
            collisionRight = tileA.isCollision() || tileB.isCollision();
        }

        // Set collision flags
        entity.setCollisionUp(collisionUp);
        entity.setCollisionDown(collisionDown);
        entity.setCollisionLeft(collisionLeft);
        entity.setCollisionRight(collisionRight);
    }
}

