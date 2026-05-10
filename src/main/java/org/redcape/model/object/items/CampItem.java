package org.redcape.model.object.items;

import org.redcape.model.object.Item;
import org.redcape.util.FileHandling;

/**
 * Camp item class
 * <p>
 * This class represents a camp item in the game.
 * It extends the SuperItem class and sets the name, type, image, and position of the camp item.
 * </p>
 */
public class CampItem extends SuperItem {

    /**
     * Constructor for CampItem
     *
     * @param x coordinate
     * @param y coordinate
     */
    public CampItem(int x, int y) {
        setName("Camp");
        setType(Item.Type.CAMP);
        setImage(FileHandling.loadImage(itemImagesPath + "camp_active.png"));
        setPosition(x, y);
    }


    public void use() {
        setInactive();
    }

    /**
     * Set the campfire inactive so it cannot be used again
     */
    public void setInactive() {
        used = true;
        setImage(FileHandling.loadImage(itemImagesPath + "camp_inactive.png"));
    }
}
