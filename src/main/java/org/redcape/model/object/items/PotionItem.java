package org.redcape.model.object.items;

import org.redcape.model.entity.Player;
import org.redcape.model.object.Item;
import org.redcape.util.FileHandling;


/**
 * PotionItem class representing a potion item in the game.
 * <p>
 * This class extends the SuperItem class and provides specific properties for the potion item.
 * </p>
 */
public class PotionItem extends SuperItem {

    private final int boostAmount;
    private final int duration;

    /**
     * Constructor for PotionItem
     *
     * @param x position
     * @param y position
     */
    public PotionItem(int x, int y) {
        setName("Potion");
        setType(Item.Type.POTION);
        setImage(FileHandling.loadImage("src/main/resources/items/potion.png"));
        setPosition(x, y);

        // Set speed increase and duration values
        this.boostAmount = 3;
        this.duration = 5;
    }

    @Override
    public void use(Player player) {
        if (player != null) {
            player.activateSpeedBoost(boostAmount, duration);
            used = true;
        }
    }
}
