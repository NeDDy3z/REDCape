package org.redcape.model.entity;

import org.redcape.view.GamePanel;

import java.awt.*;
import java.util.Random;


/**
 * Enemy class representing an enemy entity in the game.
 * <p>
 * This class extends the Entity class and implements the AI behavior for the enemy.
 * It handles the enemy's movement, collision detection, and interaction with the player.
 * </p>
 */
public final class Enemy extends Entity {

    // Parameters
    private int actionLockCounter = 0;
    private int stuckCounter = 0;
    private int lastX, lastY;

    // AI parameters
    private static final int CHASE_RADIUS = 500; // pixels
    private static final int DAMAGE = 10;
    private static final int DAMAGE_INTERVAL_MS = 500;
    private long lastDamageTime = 0;

    // Classes
    private final Player player;
    private final Random random = new Random();


    /**
     * Constructor for the Enemy class
     *
     * @param gamePanel object
     */
    public Enemy(GamePanel gamePanel) {
        super(gamePanel);
        this.player = gamePanel.getPlayer();
        setSpeed(3);
        setMaxHealth(30);
        setHealth(30);
        setDead(false);
        setCollisions();
        setDirection("down");
    }


    /**
     * Drawing method for the enemy
     *
     * @param g2 object
     */
    public void draw(Graphics2D g2) {
        if (isDead()) return;

        int screenX = getWorldX() - player.getWorldX() + player.getScreenX();
        int screenY = getWorldY() - player.getWorldY() + player.getScreenY();

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(screenX, screenY, 40, 20);

        if (isDebug()) drawHitBox(g2);
    }

    /**
     * Sets the collision area for the enemy
     */
    @Override
    public void update() {
        if (isDead()) return;

        int dx = player.getWorldX() - getWorldX();
        int dy = player.getWorldY() - getWorldY();
        double distance = Math.hypot(dx, dy);

        // Stuck detection
        if (getWorldX() == lastX && getWorldY() == lastY) {
            stuckCounter++;
        } else {
            stuckCounter = 0;
        }
        lastX = getWorldX();
        lastY = getWorldY();

        if (distance < CHASE_RADIUS) {
            chasePlayer(dx, dy);
        } else {
            wander();
        }

        if (stuckCounter > 20) {
            pickRandomDirection();
            stuckCounter = 0;
        }

        if (!isCollision()) {
            move();
        }

        // Damage the player every second if colliding
        if (collidesWithPlayer()) {
            long now = System.currentTimeMillis();
            if (now - lastDamageTime >= DAMAGE_INTERVAL_MS) {
                if (!player.isGodMode()) player.damage(DAMAGE);
                lastDamageTime = now;
            }
        }
    }

    /**
     * Function for random change of a direction
     */
    private void wander() {
        actionLockCounter++;
        if (actionLockCounter > 360) { // Change a direction every few seconds
            pickRandomDirection();
            actionLockCounter = 0;
        }
        setCollision(false);
        gamePanel.getCollisionController().checkTileCollision(this);
    }

    /**
     * Function for chasing the player when the player gets close enough
     *
     * @param dx position of the player
     * @param dy position of the player
     */
    private void chasePlayer(int dx, int dy) {
        if (dx > 0 && dy > 0) setDirection("down_right");
        else if (dx > 0 && dy < 0) setDirection("up_right");
        else if (dx < 0 && dy > 0) setDirection("down_left");
        else if (dx < 0 && dy < 0) setDirection("up_left");
        else if (dx > 0) setDirection("right");
        else if (dx < 0) setDirection("left");
        else if (dy > 0) setDirection("down");
        else if (dy < 0) setDirection("up");

        setCollision(false);
        gamePanel.getCollisionController().checkTileCollision(this);
    }

    /**
     * Randomize the wandering direction of the enemy
     */
    private void pickRandomDirection() {
        int dir = random.nextInt(8);
        switch (dir) {
            case 0 -> setDirection("up");
            case 1 -> setDirection("down");
            case 2 -> setDirection("left");
            case 3 -> setDirection("right");
            case 4 -> setDirection("up_left");
            case 5 -> setDirection("up_right");
            case 6 -> setDirection("down_left");
            case 7 -> setDirection("down_right");
        }
    }

    /**
     * Movement function for the enemy
     */
    private void move() {
        int posX = getWorldX();
        int posY = getWorldY();
        double speed = getSpeed();
        double diagSpeed = speed / Math.sqrt(2);

        switch (getDirection()) {
            case "up" -> setWorldY((int) (posY - speed));
            case "down" -> setWorldY((int) (posY + speed));
            case "left" -> setWorldX((int) (posX - speed));
            case "right" -> setWorldX((int) (posX + speed));
            case "up_left" -> {
                setWorldX((int) (posX - diagSpeed));
                setWorldY((int) (posY - diagSpeed));
            }
            case "up_right" -> {
                setWorldX((int) (posX + diagSpeed));
                setWorldY((int) (posY - diagSpeed));
            }
            case "down_left" -> {
                setWorldX((int) (posX - diagSpeed));
                setWorldY((int) (posY + diagSpeed));
            }
            case "down_right" -> {
                setWorldX((int) (posX + diagSpeed));
                setWorldY((int) (posY + diagSpeed));
            }
        }
    }

    /**
     * Check for collision with the player
     *
     * @return boolean value if the enemy collides with the player
     */
    private boolean collidesWithPlayer() {
        Rectangle enemyRect = new Rectangle(getWorldX(), getWorldY(), 32, 32);
        Rectangle playerRect = new Rectangle(player.getWorldX(), player.getWorldY(), 32, 32);
        return enemyRect.intersects(playerRect);
    }
}