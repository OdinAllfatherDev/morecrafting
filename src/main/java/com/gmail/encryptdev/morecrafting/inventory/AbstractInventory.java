package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.Log;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EncryptDev
 */
public abstract class AbstractInventory {

    protected Inventory bukkitInventory;

    public AbstractInventory(String name, int size, boolean setup) {
        if (size % 9 != 0)
            Log.throwError("Size from inventory with name [" + name + "] can not devided by 9", new RuntimeException());
        this.bukkitInventory = Bukkit.createInventory(null, size, name);
        if (setup)
            this.setup();
    }

    public abstract void setup();

    public void fill() {
        fill(0, bukkitInventory.getSize(), ItemCreator.getItem(Material.STAINED_GLASS_PANE, "§0", 1, (byte) 10));
    }

    public void fill(int from, int to) {
        fill(from, to, ItemCreator.getItem(Material.STAINED_GLASS_PANE, "§0", 1, (byte) 10));
    }

    public void fill(int from, int to, ItemStack filler) {
        for (int i = from; i < to; i++)
            bukkitInventory.setItem(i, filler);
    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public static void openInventory(Player player, AbstractInventory abstractInventory) {
        if (abstractInventory instanceof ListRecipesInventory || abstractInventory instanceof ShowRecipeInventory)
            abstractInventory.setup();
        player.openInventory(abstractInventory.getBukkitInventory());
    }

}
