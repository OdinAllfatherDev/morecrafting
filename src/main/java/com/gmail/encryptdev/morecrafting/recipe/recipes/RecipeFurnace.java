package com.gmail.encryptdev.morecrafting.recipe.recipes;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EncryptDev
 */
public class RecipeFurnace extends ARecipe {

    private ItemStack input;
    private float experience;

    public RecipeFurnace(String name, ItemStack output) {
        super(name, output);
    }

    public RecipeFurnace(Map<String, Object> map) {
        super((String) map.get("name"), (ItemStack) map.get("output"));
        this.input = (ItemStack) map.get("input");
        this.experience = (float) map.get("experience");
    }

    public RecipeFurnace setExperience(float experience) {
        this.experience = experience;
        return this;
    }

    public RecipeFurnace setInput(ItemStack input) {
        this.input = input;
        return this;
    }

    public float getExperience() {
        return experience;
    }

    @Override
    public boolean canCraft(Inventory other) {
        return true;
    }

    @Override
    public void removeItems(Inventory inventory) {
    }

    public ItemStack getInput() {
        return input;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("output", getOutput());
        map.put("experience", experience);
        map.put("input", input);
        return map;
    }

    public static RecipeFurnace deserialize(Map<String, Object> map) {
        return new RecipeFurnace(map);
    }
}
