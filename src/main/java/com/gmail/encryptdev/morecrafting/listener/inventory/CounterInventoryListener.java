package com.gmail.encryptdev.morecrafting.listener.inventory;

import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.util.HelpStorage;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by EncryptDev
 */
public class CounterInventoryListener implements Listener {

    private RecipeManager recipeManager;

    public CounterInventoryListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(MessageTranslator.getTranslatedInventoryName("counter"))) {
            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();

            if (clicked != null)
                if (clicked.hasItemMeta()) {
                    if (clicked.getItemMeta().getDisplayName().equals("§0"))
                        event.setCancelled(true);
                    String name = clicked.getItemMeta().getDisplayName();

                    //The event.setCancelled(true); call, is important, because every case statement is other item

                    ItemStack middle = event.getInventory().getItem(13).clone();
                    ItemMeta meta = middle.getItemMeta();

                    int amount = Integer.parseInt(meta.getDisplayName().split(":")[1].trim());

                    switch (name) {
                        case "§5§l-50":
                            event.setCancelled(true);
                            if(amount - 50 < 0)
                                amount = 0;
                            else
                                amount -= 50;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);
                            break;
                        case "§5§l-25":
                            event.setCancelled(true);
                            if(amount - 25 < 0)
                                amount = 0;
                            else
                                amount -= 25;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);

                            break;
                        case "§5§l-10":
                            event.setCancelled(true);
                            if(amount - 10 < 0)
                                amount = 0;
                            else
                                amount -= 10;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);

                            break;
                        case "§5§l-1":
                            event.setCancelled(true);
                            if(amount - 1 < 0)
                                amount = 0;
                            else
                                amount -= 1;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);

                            break;
                        case "§5§l+50":
                            event.setCancelled(true);
                            amount += 50;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);

                            break;
                        case "§5§l+25":
                            event.setCancelled(true);
                            amount += 25;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);

                            break;
                        case "§5§l+10":
                            event.setCancelled(true);
                            amount += 10;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);

                            break;
                        case "§5§l+1":
                            event.setCancelled(true);
                            amount += 1;
                            meta.setDisplayName("§5§lEXPERIENCE: " + amount);
                            middle.setItemMeta(meta);
                            event.getInventory().setItem(13, middle);
                            break;
                    }
                    if (name.equals(MessageTranslator.getTranslatedItemName("create-recipe-item"))) {
                        event.setCancelled(true);
                        HelpStorage helpStorage = recipeManager.getNameTable().get(player, RecipeManager.FURNACE);
                        helpStorage.setExperince(amount);
                        player.closeInventory();
                        player.sendMessage(MessageTranslator.getTranslatedMessage("set-recipe-name"));
                    }

                }
        }
    }

}
