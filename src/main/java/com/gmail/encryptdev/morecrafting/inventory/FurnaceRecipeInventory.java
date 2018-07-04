package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;

/**
 * Created by EncryptDev
 */
public class FurnaceRecipeInventory extends AbstractInventory {

    public FurnaceRecipeInventory() {
        super(MessageTranslator.getTranslatedInventoryName("create-furnace-recipe"), 18, true);
    }

    @Override
    public void setup() {
        fill();
        bukkitInventory.setItem(0, null);
        bukkitInventory.setItem(8, null);
        bukkitInventory.setItem(17, ItemCreator.getItem(Material.EMERALD, MessageTranslator.getTranslatedItemName("create-recipe-item")));
    }
}
