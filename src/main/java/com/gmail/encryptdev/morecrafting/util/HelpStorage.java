package com.gmail.encryptdev.morecrafting.util;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Created by EncryptDev
 *
 * This class, help to store any data
 */
public class HelpStorage {

    private ItemStack output;
    private Map<Integer, ItemStack> shapeItems;
    private List<ItemStack> items;
    private ItemStack input;
    private float experince;

    public HelpStorage(ItemStack output, Map<Integer, ItemStack> shapeItems) {
        this.output = output;
        this.shapeItems = shapeItems;
    }

    public HelpStorage(ItemStack output, ItemStack input) {
        this.output = output;
        this.input = input;
    }

    public HelpStorage(ItemStack output, List<ItemStack> items) {
        this.output = output;
        this.items = items;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public ItemStack getInput() {
        return input;
    }

    public float getExperince() {
        return experince;
    }

    public void setExperince(float experince) {
        this.experince = experince;
    }

    public ItemStack getOutput() {
        return output;
    }

    public Map<Integer, ItemStack> getShapeItems() {
        return shapeItems;
    }
}
