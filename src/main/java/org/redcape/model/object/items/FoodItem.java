package org.redcape.model.object.items;

import lombok.Getter;

import org.redcape.model.entity.Player;
import org.redcape.model.object.Item;
import org.redcape.util.FileHandling;


/**
 * Food item class
 * <p>
 * This class represents a food item in the game.
 * It extends the SuperItem class and sets the name, type, image, and position of the food item.
 */
public class FoodItem extends SuperItem {

    @Getter
    private final int health;

    /**
     * Constructor for FoodItem
     *
     * @param x position
     * @param y position
     */
    public FoodItem(int x, int y) {
        setName("Food");
        setType(Item.Type.FOOD);
        setImage(FileHandling.loadImage(itemImagesPath + "food.png"));
        setPosition(x, y);

        // Set health value
        this.health = 20;
    }

    @Override
    public void use(Player player) {
        player.addHealth(health);
    }
}
