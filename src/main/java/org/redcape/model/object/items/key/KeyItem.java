package org.redcape.model.object.items.key;

import org.redcape.model.object.Item;
import org.redcape.model.object.items.SuperItem;
import org.redcape.util.FileHandling;


/**
 * KeyItem class represents a key item in the game.
 * <p>
 * This class extends the SuperItem class and provides specific functionality for key items.
 * </p>
 */
public class KeyItem extends SuperItem {
    /**
     * Constructor for KeyBladeItem
     *
     * @param x position
     * @param y position
     */
    public KeyItem(int x, int y) {
        setName("Key");
        setType(Item.Type.KEY);
        setImage(FileHandling.loadImage(itemImagesPath + "key.png"));
        setPosition(x, y);
    }

    @Override
    public void use() {

    }

    @Override
    public boolean canUse() {
        return false;
    }
}
