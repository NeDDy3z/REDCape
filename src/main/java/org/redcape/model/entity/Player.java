package org.redcape.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.redcape.controller.CollisionController;
import org.redcape.controller.GameState;
import org.redcape.controller.KeyController;
import org.redcape.model.object.Inventory;
import org.redcape.model.object.Item;
import org.redcape.model.object.items.SuperItem;
import org.redcape.model.object.items.key.KeyItem;
import org.redcape.util.FileHandling;
import org.redcape.view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Player class represents the player character in the game.
 * <p>
 * This class handles player movement, collision detection, object interactions, inventory management and item use.
 * </p>
 */
@Log
@Getter
public final class Player extends Entity {

    // Player details
    @Setter
    private boolean godMode = false;

    private final Inventory inventory;

    private final Image playerSprite; // TODO add sprite animation
    private static final String PLAYER_SPRITE_PATH = "src/main/resources/sprites/player.png";

    // Potion effect
    private int potionEffect = 0;
    private int potionEffectMax = 100;
    private boolean speedBoostActive = false;
    private long speedBoostEndTime = 0;
    @Setter
    private double originalSpeed;


    // Drawing
    private final int screenX, screenY;

    // Classes
    private final CollisionController collisionController;
    private final KeyController keyController;


    /**
     * Constructor for the player entity
     *
     * @param gamePanel     instance of the game panel
     * @param keyController instance of the key controller
     */
    public Player(GamePanel gamePanel, KeyController keyController) {
        super(gamePanel);
        this.collisionController = gamePanel.getCollisionController();
        this.inventory = new Inventory(this);
        this.keyController = keyController;
        this.playerSprite = getSprite();

        gamePanel.setPlayer(this);

        resetPlayer();

        // Position
        screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        setDefaultPosition(0, 0);
    }

    /**
     * Resets the player to default values
     */
    public void resetPlayer() {
        resetEntity();

        setName("Player");
        setDescription("Player character");
        setSpeed(4);
        setOriginalSpeed(4);
        setDefaultPosition(0, 0);
    }

    /**
     * Activates a speed boost for the player
     *
     * @param boostAmount     amount of speed to add
     * @param durationSeconds duration of the speed boost in seconds
     */
    public void activateSpeedBoost(double boostAmount, int durationSeconds) {
        setSpeed(originalSpeed + boostAmount);
        speedBoostActive = true;
        speedBoostEndTime = System.currentTimeMillis() + durationSeconds * 1000;
    }

    /**
     * Draws the player on the screen
     *
     * @param g2d Graphics2D object for drawing
     */
    public void draw(Graphics2D g2d) {
//        // TODO finish sprite animation - not important for prod
//        BufferedImage sprite = null;
//
//        // Sprite type
//        String direction = "right";
//        sprite = switch (direction) {
//            case "right" -> sprite = getSprite();
//            default -> sprite = getSprite();
//        };

        g2d.drawImage(getSprite(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);

        if (isDebug()) drawHitBox(g2d);
    }

    /**
     * Updates the player parameters
     */
    public void update() {
        if (speedBoostActive && System.currentTimeMillis() > speedBoostEndTime) {
            setSpeed(originalSpeed);
            speedBoostActive = false;
        }

        updateCollisions();
        updatePosition();
        updateAction();
    }


    /**
     * Returns the sprite of the player
     *
     * @return BufferedImage of the player sprite
     */
    public BufferedImage getSprite() {
        return FileHandling.loadImage(PLAYER_SPRITE_PATH);
    }

    /**
     * Decides action with a collidable object based on Type
     *
     * @param index index of the object in the level
     */
    private void decideCollision(int index) {
        if (index != 999) {
            SuperItem object = gamePanel.getLevel().getLevelObjects().get(index);
            switch (object.getType()) {
                case Item.Type.CAMP:
                    if (object.canUse()) {
                        object.use();

                        // Create a new thread that will async save the game
                        new Thread(() -> {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            gamePanel.getGameState().setActiveState(GameState.State.SAVE);
                            log.info("Used " + object.getName());
                        }).start();
                    }
                    break;
                case Item.Type.FOOD:
                case Item.Type.KEY:
                case Item.Type.POTION:
                    if (object.canPickUp()) {
                        object.pickUp();
                        inventory.addItem(object);
                        log.info("Picked up " + object.getName());
                    }
                    break;
                case Item.Type.KEY_PART:
                    if (object.canPickUp()) {
                        object.pickUp();
                        inventory.addItem(object);
                        log.info("Picked up " + object.getName());

                        if (inventory.hasItemByName("Key")) {
                            gamePanel.showMessage("You have crafted the key!");
                        }
                    }
                    break;
                case Item.Type.END_DOOR:
                    if (inventory.hasItemByName("Key")) {
                        gamePanel.getGameState().setActiveState(GameState.State.GAME_WIN);
                    } else {
                        gamePanel.showMessage("You need a key to open this door.");
                    }
                    break;
            }
        }
    }

    /**
     * Updates the collision state of the player
     *
     * @see CollisionController
     */
    private void updateCollisions() {
        // Reset collision flags
        setCollision(false);

        // Check for collisions
        if (!godMode) collisionController.checkTileCollision(this);
        int objIndex = collisionController.checkObjectCollision(this, true);

        // Check for collision with objects and decide action
        decideCollision(objIndex);
        objIndex = 999;
    }


    /**
     * Update player position based on key input
     *
     * @see KeyController
     */
    private void updatePosition() {
        // Helper variables
        int posX = getWorldX();
        int posY = getWorldY();
        double speed = getSpeed();


        // Set direction
        if (keyController.isMoveUp()) {
            setDirection("up");
        }
        if (keyController.isMoveDown()) {
            setDirection("down");
        }
        if (keyController.isMoveLeft()) {
            setDirection("left");
        }
        if (keyController.isMoveRight()) {
            setDirection("right");
        }

        // Movement logic
        if (keyController.isMoveUp() && !isCollisionUp()) {
            setWorldY((int) (posY - speed));
        }
        if (keyController.isMoveDown() && !isCollisionDown()) {
            setWorldY((int) (posY + speed));
        }
        if (keyController.isMoveLeft() && !isCollisionLeft()) {
            setWorldX((int) (posX - speed));
        }
        if (keyController.isMoveRight() && !isCollisionRight()) {
            setWorldX((int) (posX + speed));
        }
    }

    /**
     * Update inventory-item action based on key input.
     * Data is hardcoded because inventory is not dynamic.
     *
     * @see Inventory
     */
    private void updateAction() {
        for (int i = 0; i < inventory.getItems().length; i++) {
            if (keyController.isItem(i+1) &&
                    inventory.getItems()[i] != null &&
                    inventory.getItems()[i].canUse()
            ) {
                inventory.useItem(inventory.getItems()[i]);
            }
        }

        keyController.resetItemFlags();
    }
}
