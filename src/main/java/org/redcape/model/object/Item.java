package org.redcape.model.object;

import org.redcape.model.entity.Player;

import java.awt.image.BufferedImage;


/**
 * Interface representing an item in the game.
 * <p>
 * This interface defines the basic structure and behavior of an item in the game.
 * </p>
 */
public interface Item {

    /**
     * Directory path to item images.
     */
    String itemImagesPath = "src/main/resources/items/";

    /**
     * Item types.
     */
    enum Type {
        CAMP,
        END_DOOR,
        FOOD,
        KEY,
        KEY_PART,
        NONE,
        POTION,
    }

    /**
     * Get the name of the item.
     *
     * @return name of the item.
     */
    String getName();

    /**
     * Get the type of the item.
     *
     * @return Type of the item.
     */
    Type getType();


    /**
     * Return the image sprite of the item.
     *
     * @return Image of the item.
     */
    BufferedImage getImage();

    /**
     * Use the item.
     */
    void use();

    /**
     * Use the item on player.
     *
     * @param player object
     */
    void use(Player player);

    /**
     * Return if the item can be used.
     *
     * @return true if the item can be used, false otherwise
     */
    boolean canUse();

    /**
     * Pick up the item.
     */
    void pickUp();

    /**
     * Return if the item can be picked up.
     *
     * @return true if the item can be picked up, false otherwise
     */
    boolean canPickUp();
}
