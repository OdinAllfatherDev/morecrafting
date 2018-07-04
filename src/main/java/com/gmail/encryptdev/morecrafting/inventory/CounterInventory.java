package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;

/**
 * Created by EncryptDev
 */
public class CounterInventory extends AbstractInventory {

    public CounterInventory() {
        super(MessageTranslator.getTranslatedInventoryName("counter"), 27, true);
    }

    @Override
    public void setup() {
        fill();

        bukkitInventory.setItem(9, ItemCreator.getItem(Material.ARROW, "§5§l-50"));
        bukkitInventory.setItem(10, ItemCreator.getItem(Material.ARROW, "§5§l-25"));
        bukkitInventory.setItem(11, ItemCreator.getItem(Material.ARROW, "§5§l-10"));
        bukkitInventory.setItem(12, ItemCreator.getItem(Material.ARROW, "§5§l-1"));
        bukkitInventory.setItem(13, ItemCreator.getItem(Material.GOLD_NUGGET, "§eEXPERIENCE: 0"));
        bukkitInventory.setItem(14, ItemCreator.getItem(Material.ARROW, "§5§l+1"));
        bukkitInventory.setItem(15, ItemCreator.getItem(Material.ARROW, "§5§l+10"));
        bukkitInventory.setItem(16, ItemCreator.getItem(Material.ARROW, "§5§l+25"));
        bukkitInventory.setItem(17, ItemCreator.getItem(Material.ARROW, "§5§l+50"));

        bukkitInventory.setItem(26, ItemCreator.getItem(Material.EMERALD, MessageTranslator.getTranslatedItemName("create-recipe-item")));
    }
}
