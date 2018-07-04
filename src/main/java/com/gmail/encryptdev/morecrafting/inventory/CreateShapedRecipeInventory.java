package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;

/**
 * Created by EncryptDev
 */
public class CreateShapedRecipeInventory extends WorkbenchInventory {

    public CreateShapedRecipeInventory(boolean shapeless) {
        super(shapeless ? MessageTranslator.getTranslatedInventoryName("create-shapeless-recipe")
                : MessageTranslator.getTranslatedInventoryName("create-shaped-recipe"), true);
    }

    @Override
    public void setup() {
        super.setup();

        bukkitInventory.setItem(52, ItemCreator.getItem(Material.EMERALD, MessageTranslator.getTranslatedItemName("create-recipe-item")));
    }
}
