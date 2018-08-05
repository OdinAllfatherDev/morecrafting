package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;

/**
 * Created by EncryptDev
 */
public class WorkbenchInventory extends AbstractInventory {

    private HashMap<Integer, Integer[]> rows;

    public WorkbenchInventory(boolean setup) {
        this(ChatColor.translateAlternateColorCodes('&',
                (String) MoreCrafting.getInstance().getJsonLoader().getRecipeSettingsFile().getJsonObject("custom-workbench").get("name")), setup);
    }

    public WorkbenchInventory(String name, boolean setup) {
        super(name, 54, setup);
    }

    @Override
    public void setup() {
        fill();
        this.rows = new HashMap<>();
        this.rows.put(0, new Integer[]{0, 1, 2, 3, 4});
        this.rows.put(1, new Integer[]{9, 10, 11, 12, 13});
        this.rows.put(2, new Integer[]{18, 19, 20, 21, 22});
        this.rows.put(3, new Integer[]{27, 28, 29, 30, 31});
        this.rows.put(4, new Integer[]{36, 37, 38, 39, 40});
        for (int row : rows.keySet()) {
            Integer[] slots = rows.get(row);

            for (int slot : slots) {
                bukkitInventory.setItem(slot, null);
            }
        }
        bukkitInventory.setItem(52, ItemCreator.getItem(Material.DIAMOND, MessageTranslator.getTranslatedItemName("craft-item")));
        bukkitInventory.setItem(53, null);
    }


}
