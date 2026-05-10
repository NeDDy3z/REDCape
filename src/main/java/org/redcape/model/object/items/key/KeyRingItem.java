package org.redcape.model.object.items.key;

import org.redcape.model.object.Item;
import org.redcape.model.object.items.SuperItem;
import org.redcape.util.FileHandling;

/**
 * KeyRingItem class
 * <p>
 * This class represents a part of the key item in the game.
 * It extends the KeyItem class and sets the name, type, image, and position of the key ring item.
 * </p>
 */
public class KeyRingItem extends SuperItem {
    /**
     * Constructor for KeyBladeItem
     *
     * @param x position
     * @param y position
     */
    public KeyRingItem(int x, int y) {
        setName("Key Ring");
        setType(Item.Type.KEY_PART);
        setImage(FileHandling.loadImage(itemImagesPath + "key_ring.png"));
        setPosition(x, y);
    }
}
