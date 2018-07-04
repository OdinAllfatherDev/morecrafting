package com.gmail.encryptdev.morecrafting.listener;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.inventory.AbstractInventory;
import com.gmail.encryptdev.morecrafting.inventory.WorkbenchInventory;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EncryptDev
 */
public class WorkbenchInteractListener implements Listener {

    private List<Player> notPlace;

    public WorkbenchInteractListener() {
        this.notPlace = new ArrayList<>();
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().hasMetadata(MoreCrafting.CRAFTING_META_DATA)) {
                event.setCancelled(true);
                AbstractInventory.openInventory(player, new WorkbenchInventory(true));
            }

        }

    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        if (notPlace.contains(event.getPlayer()))
            event.setBuild(false);
        else
            event.setBuild(true);
    }

    @EventHandler
    public void on(InventoryCloseEvent event) {
        if (event.getInventory().getName() != null)
            if (event.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&',
                    (String) MoreCrafting.getInstance().getJsonLoader().getRecipeSettingsFile().getJsonObject("custom-workbench").get("name")))) {
                Player player = (Player) event.getPlayer();
                if (notPlace.contains(player))
                    notPlace.remove(player);

                ItemStack[] invContent = new ItemStack[25];

                int index = 0;

                for (ItemStack itemStack : event.getInventory().getContents()) {
                    if (itemStack == null)
                        continue;
                    if (itemStack.hasItemMeta()) {
                        if (itemStack.getItemMeta().getDisplayName().equals("§0"))
                            continue;
                        if (itemStack.getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("craft-item")))
                            continue;
                    }

                    invContent[index++] = itemStack;
                }

                invContent = Arrays.copyOfRange(invContent, 0, index);
                player.getInventory().addItem(invContent);

            }
    }

}
