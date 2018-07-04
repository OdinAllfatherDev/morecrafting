package com.gmail.encryptdev.morecrafting.recipe;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by EncryptDev
 */
public class RecipeScanner {

    public RecipeScanner() {

    }

    public List<ItemStack> scanShapelessShape(Inventory inventory) {
        Validate.notNull(inventory, "can not scan the inventory, the inventory is null");
        List<ItemStack> shapeItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.add(inventory.getItem(i));
        }

        for (int i = 9; i < 14; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.add(inventory.getItem(i));
        }

        for (int i = 18; i < 23; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.add(inventory.getItem(i));
        }

        for (int i = 27; i < 32; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.add(inventory.getItem(i));
        }

        for (int i = 36; i < 41; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.add(inventory.getItem(i));
        }
        return shapeItems;
    }

    public Map<Integer, ItemStack> scanShapedShape(Inventory inventory) {
        Validate.notNull(inventory, "can not scan the inventory, the inventory is null");

        Map<Integer, ItemStack> shapeItems = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.put(i, inventory.getItem(i));
        }

        for (int i = 9; i < 14; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.put(i, inventory.getItem(i));
        }

        for (int i = 18; i < 23; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.put(i, inventory.getItem(i));
        }

        for (int i = 27; i < 32; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.put(i, inventory.getItem(i));
        }

        for (int i = 36; i < 41; i++) {
            if (inventory.getItem(i) == null)
                continue;
            shapeItems.put(i, inventory.getItem(i));
        }

        return shapeItems;
    }

}
