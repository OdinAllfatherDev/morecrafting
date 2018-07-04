package com.gmail.encryptdev.morecrafting.listener.inventory;

import com.gmail.encryptdev.morecrafting.inventory.AbstractInventory;
import com.gmail.encryptdev.morecrafting.inventory.CounterInventory;
import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeFurnace;
import com.gmail.encryptdev.morecrafting.util.HelpStorage;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EncryptDev
 */
public class CreateFurnaceRecipeInventoryListener implements Listener {

    private RecipeManager recipeManager;

    public CreateFurnaceRecipeInventoryListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(MessageTranslator.getTranslatedInventoryName("create-furnace-recipe"))) {
            Player player = (Player) event.getWhoClicked();
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack != null)
                if (itemStack.hasItemMeta())
                    if (itemStack.getItemMeta().getDisplayName() != null)
                        if (itemStack.getItemMeta().getDisplayName().equals("§0"))
                            event.setCancelled(true);
                        else if (itemStack.getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("create-recipe-item"))) {
                            event.setCancelled(true);
                            if (event.getInventory().getItem(0) == null) {
                                event.getInventory().setItem(16, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("set-input-item")));
                                return;
                            }
                            if (event.getInventory().getItem(8) == null) {
                                event.getInventory().setItem(16, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("set-output-item")));
                                return;
                            }

                            ItemStack input = event.getInventory().getItem(0);
                            ItemStack output = event.getInventory().getItem(8);

                            RecipeFurnace furnaceRecipe = recipeManager.findFurnaceRecipe(output, input);
                            if (furnaceRecipe == null) {
                                event.getInventory().setItem(16, ItemCreator.getItem(Material.STAINED_GLASS_PANE, "§0", 1, (byte) 10));
                                AbstractInventory.openInventory(player, new CounterInventory());
                                recipeManager.getNameTable().put(player, RecipeManager.FURNACE, new HelpStorage(output, input));
                            } else {
                                event.getInventory().setItem(16, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("recipe-already-exist")));
                            }

                        }

        }
    }

}
