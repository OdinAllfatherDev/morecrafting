package com.gmail.encryptdev.morecrafting.listener;

import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EncryptDev
 */
public class RemovedRecipeListener implements Listener {

    private RecipeManager recipeManager;

    public RemovedRecipeListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @EventHandler
    public void on(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null) {
            Material material = event.getRecipe().getResult().getType();
            short itemData = event.getRecipe().getResult().getDurability();

            if (recipeManager.getItemMatData().containsKey(material)) {
                short data = recipeManager.getItemMatData().get(material);
                if (itemData == data)
                    event.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }

    }

}
