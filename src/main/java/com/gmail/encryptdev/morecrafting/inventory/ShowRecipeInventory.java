package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.recipe.recipes.ARecipe;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeFurnace;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShaped;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShapeless;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Created by EncryptDev
 */
public class ShowRecipeInventory extends WorkbenchInventory {

    private String name;

    public ShowRecipeInventory(String name) {
        super(MessageTranslator.getTranslatedInventoryName("show-recipe"), false);
        this.name = name;
    }

    @Override
    public void setup() {
        super.setup();
        bukkitInventory.setItem(52, ItemCreator.getItem(Material.STAINED_GLASS_PANE, "§0", 0, (byte) 10));
        ARecipe recipe = MoreCrafting.getInstance().getRecipeManager().getRecipeByName(ChatColor.stripColor(name));
        if (recipe instanceof RecipeShaped) {
            Map<Integer, ItemStack> shape = ((RecipeShaped) recipe).getFinalRecipeMap();

            for (int key : shape.keySet()) {
                bukkitInventory.setItem(key, shape.get(key));
            }
            bukkitInventory.setItem(53, recipe.getOutput());
        } else if (recipe instanceof RecipeShapeless) {
            List<ItemStack> items = ((RecipeShapeless) recipe).getIngredients();

            int slot = 0;

            for (int i = 0; i < items.size(); i++) {
                if (slot == 5) {
                    slot = 9;
                } else if (slot == 14) {
                    slot = 18;
                } else if (slot == 23) {
                    slot = 27;
                } else if (slot == 32) {
                    slot = 36;
                } else if (slot == 41) {
                    break;
                }

                bukkitInventory.setItem(slot, items.get(i));

                slot++;
            }
            bukkitInventory.setItem(53, recipe.getOutput());
        } else if (recipe instanceof RecipeFurnace) {
            RecipeFurnace recipeFurnace = (RecipeFurnace) recipe;
            ItemStack input = recipeFurnace.getInput();
            ItemStack output = recipeFurnace.getOutput();

            bukkitInventory.setItem(2, input);
            bukkitInventory.setItem(38, output);
            bukkitInventory.setItem(53, recipe.getOutput());
        }

    }
}
