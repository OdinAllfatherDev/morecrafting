package com.gmail.encryptdev.morecrafting.listener.inventory;

import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShaped;
import com.gmail.encryptdev.morecrafting.util.HelpStorage;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by EncryptDev
 */
public class CreateShapedRecipeInventoryListener implements Listener {

    private RecipeManager recipeManager;

    public CreateShapedRecipeInventoryListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(MessageTranslator.getTranslatedInventoryName("create-shaped-recipe"))) {
            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            if (clicked != null)
                if (clicked.hasItemMeta()) {
                    if (clicked.getItemMeta().getDisplayName() != null) {
                        if (clicked.getItemMeta().getDisplayName().equals("§0"))
                            event.setCancelled(true);
                        else if (clicked.getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("create-recipe-item"))) {
                            event.setCancelled(true);

                            if (event.getInventory().getItem(53) == null) {
                                event.getInventory().setItem(51, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("set-output-item")));
                                return;
                            }

                            Map<Integer, ItemStack> shapeItems = recipeManager.getRecipeScanner().scanShapedShape(event.getInventory());
                            if (shapeItems.isEmpty()) {
                                event.getInventory().setItem(51, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("set-input-item")));
                                return;
                            }
                            RecipeShaped recipeShaped = recipeManager.findShapedRecipe(event.getInventory());
                            if (recipeShaped == null) {
                                event.getInventory().setItem(51, ItemCreator.getItem(Material.STAINED_GLASS_PANE, "§0", 1, (byte) 10));
                                recipeManager.getNameTable().put(player, RecipeManager.SHAPED, new HelpStorage(event.getInventory().getItem(53), shapeItems));
                                player.closeInventory();
                                player.sendMessage(MessageTranslator.getTranslatedMessage("set-recipe-name"));
                            } else {
                                event.getInventory().setItem(51, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("recipe-already-exist")));
                            }

                        }
                    }
                }
        }
    }

}
