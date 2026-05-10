package org.redcape.model.object.items;

import org.redcape.model.object.Item;
import org.redcape.util.FileHandling;


/**
 * EndDoor class representing an end door item in the game.
 * <p>
 * This class extends the SuperItem class and provides specific properties for the end door item.
 * </p>
 */
public class EndDoor extends SuperItem {

    /**
     * Constructor for EndDoor
     *
     * @param x coordinate
     * @param y coordinate
     */
    public EndDoor(int x, int y) {
        setName("End Door");
        setType(Item.Type.END_DOOR);
        setImage(FileHandling.loadImage(itemImagesPath + "door.png"));
        setPosition(x, y);

        used = false;
    }

    @Override
    public void use() {
        System.out.println("End door used!");
    }
}
