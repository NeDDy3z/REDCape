package org.redcape.model.object;

import lombok.Getter;
import lombok.extern.java.Log;

import org.redcape.model.entity.Player;
import org.redcape.model.object.items.SuperItem;


/**
 * Inventory class for the game
 * <p>
 * This class represents the inventory of the player in the game.
 * It contains a fixed size of 5 items.
 * </p>
 */
@Getter
@Log
public final class Inventory {

    /**
     * Static size of the inventory
     */
    public final static int SIZE = 5;

    private final Player player;
    private final Item[] items = new SuperItem[SIZE];

    /**
     * Constructor
     *
     * @param player the player who owns the inventory
     */
    public Inventory(Player player) {
        this.player = player;
    }


    /**
     * Get a stringified version of the inventory
     *
     * @return stringified inventory with items
     */
    public String[] getItemsInString() {
        String[] itemsInString = new String[SIZE];

        for (int i = 0; i < SIZE; i++) {
            if (items[i] != null) {
                itemsInString[i] = items[i].getName();
            } else {
                itemsInString[i] = "x";
            }
        }

        return itemsInString;
    }

    /**
     * Load items from a string array
     * This method is used to load items from saved game data
     *
     * @param itemsInString data object
     */
    public void loadItems(String[] itemsInString) {
        for (int i = 0; i < SIZE; i++) {
            if (!itemsInString[i].equals("x")) {
                items[i] = ItemFactory.createItem(itemsInString[i], 0, 0);
            } else {
                items[i] = null;
            }
        }
    }

    /**
     * Add an item to the inventory
     *
     * @param item to be added
     */
    public void addItem(Item item) {
        // Check if the inventory is full
        boolean fullInventory = true;

        for (int i = 0; i < SIZE; i++) {
            if (items[i] == null) {
                fullInventory = false;
                break;
            }
        }

        if (fullInventory) {
            log.info("Tried to pick up an item on full Inventory");
            return;
        }

        for (int i = 0; i < SIZE; i++) {
            if (items[i] == null) {
                items[i] = item;
                tryToConstructKey();
                break;
            }
        }
    }

    private void tryToConstructKey() {
        // Check if the player has the key blade and key ring
        if (hasItemByName("Key Blade") && hasItemByName("Key Ring")) {
            // Remove the key blade and key ring from the inventory
            removeItem(getItemByName("Key Blade"));
            removeItem(getItemByName("Key Ring"));

            // Create the key
            SuperItem key = ItemFactory.createItem("Key", 0, 0);
            addItem(key);
        }
    }


    /**
     * Use an item from the inventory
     *
     * @param item to be used
     */
    public void useItem(Item item) {
        for (int i = 0; i < SIZE; i++) {
            if (items[i] == item) {
                // Use the item
                item.use(player);

                // Remove the item from the inventory
                removeItem(item);
                break;
            }
        }
    }

    /**
     * Check if an item is in the inventory
     *
     * @param name of the searched item
     * @return true if the item is in the inventory, false otherwise
     */
    public boolean hasItemByName(String name) {
        for (int i = 0; i < SIZE; i++) {
            if (items[i] != null && items[i].getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get item object by name
     *
     * @param name of the searched item
     * @return the item if it is in the inventory, null otherwise
     */
    public SuperItem getItemByName(String name) {
        for (int i = 0; i < SIZE; i++) {
            if (items[i] != null && items[i].getName().equals(name)) {
                return (SuperItem) items[i];
            }
        }

        return null;
    }

    /**
     * Remove an item from the inventory
     *
     * @param item to be removed
     */
    private void removeItem(Item item) {
        for (int i = 0; i < SIZE; i++) {
            if (items[i] == item) {
                items[i] = null;
                break;
            }
        }
    }
}
