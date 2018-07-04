package com.gmail.encryptdev.morecrafting.listener.inventory;

import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShapeless;
import com.gmail.encryptdev.morecrafting.util.HelpStorage;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by EncryptDev
 */
public class CreateShapelessRecipeInventoryListener implements Listener {

    private RecipeManager recipeManager;

    public CreateShapelessRecipeInventoryListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(MessageTranslator.getTranslatedInventoryName("create-shapeless-recipe"))) {
            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            if (clicked != null)
                if (clicked.hasItemMeta()) {
                    if (clicked.getItemMeta().getDisplayName() != null)
                        if (clicked.getItemMeta().getDisplayName().equals("§0"))
                            event.setCancelled(true);
                        else if (clicked.getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("create-recipe-item"))) {
                            if (event.getInventory().getItem(53) == null) {
                                event.getInventory().setItem(51, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("set-output-item")));
                                return;
                            }
                            List<ItemStack> items = recipeManager.getRecipeScanner().scanShapelessShape(event.getInventory());
                            if (items.isEmpty()) {
                                event.getInventory().setItem(51, ItemCreator.getItem(Material.BARRIER,
                                        MessageTranslator.getTranslatedItemName("set-input-item")));
                                return;
                            }
                            RecipeShapeless recipeShaped = recipeManager.findShapelessRecipe(event.getInventory());
                            if (recipeShaped == null) {
                                event.getInventory().setItem(51, ItemCreator.getItem(Material.STAINED_GLASS_PANE, "§0", 1, (byte) 10));
                                recipeManager.getNameTable().put(player, RecipeManager.SHAPELESS, new HelpStorage(event.getInventory().getItem(53), items));
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
