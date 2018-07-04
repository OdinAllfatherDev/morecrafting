package com.gmail.encryptdev.morecrafting.listener;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by EncryptDev
 */
public class WorkbenchBreakListener implements Listener {

    @EventHandler
    public void on(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.WORKBENCH) {
            if (event.getBlock().hasMetadata(MoreCrafting.CRAFTING_META_DATA)) {
                Location location = event.getBlock().getLocation();
                MoreCrafting.getInstance().getBlockListFile().removeBlock(location);
            }
        }
    }

}
