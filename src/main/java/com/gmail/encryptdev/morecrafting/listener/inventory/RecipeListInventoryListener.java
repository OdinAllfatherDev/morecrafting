package com.gmail.encryptdev.morecrafting.listener.inventory;

import com.gmail.encryptdev.morecrafting.inventory.AbstractInventory;
import com.gmail.encryptdev.morecrafting.inventory.ListRecipesInventory;
import com.gmail.encryptdev.morecrafting.inventory.ShowRecipeInventory;
import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.util.Log;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.InvalidObjectException;

/**
 * Created by EncryptDev
 */
public class RecipeListInventoryListener implements Listener {

    private String inventoryName;
    private String first;
    private RecipeManager recipeManager;

    public RecipeListInventoryListener(RecipeManager recipeManager) {
        this.inventoryName = MessageTranslator.getTranslatedInventoryName("list-recipes");
        if(!this.inventoryName.contains("#"))
            Log.throwError("The list-recipes name, must one of the following character before the page number: #",
                    new InvalidObjectException(""));
        this.first = inventoryName.split("#")[0];
        this.recipeManager = recipeManager;
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        if(event.getInventory().getName().startsWith(first)) {
            Player player = (Player) event.getWhoClicked();
            int page = Integer.parseInt(event.getInventory().getName().split("#")[1]);
            if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta())
                return;

            if(event.getInventory().getName().equals(inventoryName.replace("{Page}", String.valueOf(page)))) {
                event.setCancelled(true);
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("page-next"))) {
                    if(page >= recipeManager.getMaxPages()) {
                        event.setCancelled(true);
                    } else {
                        AbstractInventory.openInventory(player, new ListRecipesInventory(page + 1));
                    }
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("page-back"))) {
                    if(page <= 1) {
                        event.setCancelled(true);
                    } else {
                        AbstractInventory.openInventory(player, new ListRecipesInventory(page - 1));
                    }
                } else {
                    if(!event.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
                        String displayName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                        if(recipeManager.getRecipeByName(displayName) != null)
                            AbstractInventory.openInventory(player, new ShowRecipeInventory(displayName));
                    }
                }
            }

        }

    }

}
