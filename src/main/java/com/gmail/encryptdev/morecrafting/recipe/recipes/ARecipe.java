package com.gmail.encryptdev.morecrafting.recipe.recipes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EncryptDev
 */
public abstract class ARecipe implements ConfigurationSerializable {

    private String name;
    private ItemStack output;

    public ARecipe(String name, ItemStack output) {
        this.name = name;
        this.output = output;
    }

    public abstract boolean canCraft(Inventory inventory);

    public abstract void removeItems(Inventory inventory);

    public String getName() {
        return name;
    }

    public ItemStack getOutput() {
        return output;
    }
}
