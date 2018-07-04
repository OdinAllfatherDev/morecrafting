package com.gmail.encryptdev.morecrafting.listener.inventory;

import com.gmail.encryptdev.morecrafting.inventory.AbstractInventory;
import com.gmail.encryptdev.morecrafting.inventory.CreateShapedRecipeInventory;
import com.gmail.encryptdev.morecrafting.inventory.FurnaceRecipeInventory;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EncryptDev
 */
public class ChooseRecipeInventoryListener implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(MessageTranslator.getTranslatedInventoryName("choose-recipe"))) {
            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            if (clicked != null)
                if (clicked.hasItemMeta()) {
                    event.setCancelled(true);
                    if (clicked.getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("shaped-recipe-item")))
                        AbstractInventory.openInventory(player, new CreateShapedRecipeInventory(false));
                    else if (clicked.getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("shapeless-recipe-item")))
                        AbstractInventory.openInventory(player, new CreateShapedRecipeInventory(true));
                    else if (clicked.getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("furnace-recipe-item")))
                        AbstractInventory.openInventory(player, new FurnaceRecipeInventory());
                }
        }
    }

}
