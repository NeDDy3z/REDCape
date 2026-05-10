package org.redcape.model.object;

import lombok.extern.java.Log;
import org.redcape.model.object.items.*;
import org.redcape.model.object.items.key.KeyBladeItem;
import org.redcape.model.object.items.key.KeyItem;
import org.redcape.model.object.items.key.KeyRingItem;


/**
 * ItemFactory class for creating items
 * <p>
 * This class is responsible for creating items based on their name.
 * It can create a camp item, food item, potion item, or a random item.
 * </p>
 */
@Log
public final class ItemFactory {

    /**
     * Private constructor to prevent instantiation
     */
    private ItemFactory() {
    }

    /**
     * Get a new item based on the name.
     *
     * @param name of the item
     * @param x    position
     * @param y    position
     * @return SuperItem
     */
    public static SuperItem createItem(String name, int x, int y) {
        SuperItem item = decideItem(name, x, y);
        item.setPickedUp(false);
        return item;
    }

    /**
     * Get a new item based on the name and set it as picked up or used.
     * The item will load as inactive if it is a camp item. Or will not load at all if it's a regular item
     *
     * @param name of the item
     * @param x    position
     * @param y    position
     * @return SuperItem
     */
    public static SuperItem createPickedUpItem(String name, int x, int y) {
        SuperItem item = decideItem(name, x, y);

        if (item instanceof CampItem) {
            item = new CampItem(x, y);
            ((CampItem) item).setInactive();
        } else {
            item.setPickedUp(true);
        }

        return item;
    }

    /**
     * Get a random item.
     *
     * @param x position
     * @param y position
     * @return SuperItem
     */
    private static SuperItem getRandomItem(int x, int y) {
        SuperItem[] items = new SuperItem[]{
                new CampItem(x, y),
                new FoodItem(x, y),
                new PotionItem(x, y)
        };

        int randomIndex = (int) (Math.random() * items.length);
        return items[randomIndex];
    }

    /**
     * Returns the item based on the name.
     *
     * @param name of the item
     * @param x position
     * @param y position
     * @return SuperItem
     */
    private static SuperItem decideItem(String name, int x, int y) {
        if (name == null) name = "";
        return switch (name.toLowerCase()) {
            case "camp" -> new CampItem(x, y);
            case "end" -> new EndDoor(x, y);
            case "food" -> new FoodItem(x, y);
            case "potion" -> new PotionItem(x, y);
            case "key" -> new KeyItem(x, y);
            case "key ring" -> new KeyRingItem(x, y);
            case "key blade" -> new KeyBladeItem(x, y);
            default -> getRandomItem(x, y);
        };
    }
}
