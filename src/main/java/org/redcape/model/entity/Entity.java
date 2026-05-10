package org.redcape.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.redcape.view.GamePanel;

import java.awt.*;


/**
 * Entity class representing a game entity.
 * <p>
 * This class serves as a base class for all entities in the game, including players and enemies.
 * It handles the entity's position, movement, and collision detection.
 * </p>
 */
@Getter
@Setter
public class Entity {

    // Details
    private String name;
    private String description;
    private Image sprite;
    private boolean debug = false;

    private int health;
    private int maxHealth = 1;
    private boolean dead = false;

    // Movement
    private double speed;
    private double maxSpeed = 5;
    private boolean collisionUp = false, collisionDown = false, collisionLeft = false, collisionRight = false;
    private String direction = "up";
    private int worldX, worldY;

    // Collision
    public Rectangle collisionArea;
    private int collisionAreaDefaultX, collisionAreaDefaultY;
    private boolean collision = false;

    // Classes
    public final GamePanel gamePanel;


    /**
     * Constructor for the Entity class
     *
     * @param gamePanel object
     */
    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        resetEntity();
    }

    /**
     * Draw the entity on the screen.
     *
     * @param g2d object
     */
    public void draw(Graphics2D g2d) {}

    /**
     * Draw the entity's hitbox for debugging purposes.
     *
     * @param g2d object
     */
    public void drawHitBox(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(
                getWorldX() + collisionArea.x - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX(),
                getWorldY() + collisionArea.y - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY(),
                collisionArea.width,
                collisionArea.height
        );
    }

    /**
     * Update the entity's details and state.
     */
    public void update() {}

    /**
     * Reset entity to default values.
     */
    public void resetEntity() {
        setName("Entity");
        setDescription("Entity character");
        setSpeed(2);
        setMaxHealth(100);
        setHealth(getMaxHealth()/2);
        setDead(false);
        setCollisions();
        setDefaultPosition(50,50);
    }

    /**
     * Set the default position of the entity.
     *
     * @param x coordinate
     * @param y coordinate
     */
    public void setDefaultPosition(int x, int y) {
        setWorldX(x * gamePanel.getTileSize());
        setWorldY(y * gamePanel.getTileSize());
    }

    /**
     * Set world position (x and y multiplied by tileSize) of the enemy
     *
     * @param position on the map
     */
    public void setWorldPosition(int[] position) {
        setWorldX(position[0]);
        setWorldY(position[1]);
    }

    /**
     * Get the world X coordinate of the entity.
     *
     * @return world position of the entity
     */
    public int[] getPosition() {
        return new int[]{getWorldX(), getWorldY()};
    }

    /**
     * Set the collision area of the entity.
     */
    public void setCollisions() {
        collisionArea = new Rectangle();
        collisionArea.setBounds(0,0, 25, 25);
        collisionAreaDefaultX = 19;
        collisionAreaDefaultY = 28;
    }

    /**
     * Set health to the entity.
     *
     * @param health amount
     */
    public void setHealth(int health) {
        if (health <= maxHealth) {
            this.health = health;
        }
    }

    /**
     * Add health to the entities health - used in healing items
     *
     * @param amount of health to add
     */
    public void addHealth(int amount) {
        setHealth(getHealth() + amount);
        if (getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
    }

    /**
     * Damage the entity.
     * If the entity's health is lower than or equal to 0, execute death() method.
     *
     * @param damage amount
     */
    public void damage(int damage) {
        if (this.health - damage > 0) {
            this.health -= damage;
        } else {
            death();
        }
    }

    /**
     * Declare entity as dead.
     */
    public void death() {
        dead = true;
    }
}
