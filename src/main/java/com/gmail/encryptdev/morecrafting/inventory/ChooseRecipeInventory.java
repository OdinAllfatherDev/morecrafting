package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;

import java.util.Arrays;

/**
 * Created by EncryptDev
 */
public class ChooseRecipeInventory extends AbstractInventory {

    public ChooseRecipeInventory() {
        super(MessageTranslator.getTranslatedInventoryName("choose-recipe"), 9, true);
    }

    @Override
    public void setup() {
        fill();
        bukkitInventory.setItem(0, ItemCreator.getItem(Material.PAPER, MessageTranslator.getTranslatedItemName("shaped-recipe-item"), 1,
                (byte) 0, Arrays.asList(MessageTranslator.getTranslatedLoreString("shaped-recipe-item-lore"))));
        bukkitInventory.setItem(4, ItemCreator.getItem(Material.EMPTY_MAP, MessageTranslator.getTranslatedItemName("shapeless-recipe-item"), 1,
                (byte) 0, Arrays.asList(MessageTranslator.getTranslatedLoreString("shapeless-recipe-item-lore"))));
        bukkitInventory.setItem(8, ItemCreator.getItem(Material.FURNACE, MessageTranslator.getTranslatedItemName("furnace-recipe-item"), 1,
                (byte) 0, Arrays.asList(MessageTranslator.getTranslatedLoreString("furnace-recipe-item-lore"))));
    }
}
