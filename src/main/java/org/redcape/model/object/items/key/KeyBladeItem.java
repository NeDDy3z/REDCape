package org.redcape.model.object.items.key;


import org.redcape.model.object.Item;
import org.redcape.model.object.items.SuperItem;
import org.redcape.util.FileHandling;

/**
 * KeyBladeItem class
 * <p>
 * This class represents a part of the key item in the game.
 * It extends the SuperItem class and sets the name, type, image, and position of the key blade item.
 * </p>
 */
public class KeyBladeItem extends SuperItem {
    /**
     * Constructor for KeyBladeItem
     *
     * @param x position
     * @param y position
     */
    public KeyBladeItem(int x, int y) {
        setName("Key Blade");
        setType(Item.Type.KEY_PART);
        setImage(FileHandling.loadImage( itemImagesPath+ "key_blade.png"));
        setPosition(x, y);
    }
}
