package com.gmail.encryptdev.morecrafting.listener;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.json.JsonLoader;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by EncryptDev
 */
public class WorkbenchPlaceListener implements Listener {

    private JsonLoader jsonLoader;

    public WorkbenchPlaceListener(JsonLoader jsonLoader) {
        this.jsonLoader = jsonLoader;
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack inHand = player.getInventory().getItemInMainHand();

        if(inHand != null)
            if(inHand.hasItemMeta())
                if(inHand.getItemMeta().hasDisplayName())
                    if(inHand.getItemMeta().getDisplayName()
                            .equals(ChatColor.translateAlternateColorCodes('&',
                                    (String) jsonLoader.getRecipeSettingsFile().getJsonObject("custom-workbench").get("name")))) {
                        event.getBlock().setMetadata(MoreCrafting.CRAFTING_META_DATA, new FixedMetadataValue(MoreCrafting.getInstance(), "CUSTOM-CRAFTER"));

                        if(MoreCrafting.getInstance().getConfig().getBoolean("block-owner")) {
                            event.getBlock().setMetadata(MoreCrafting.BLOCK_OWNER_META_DATA, new FixedMetadataValue(MoreCrafting.getInstance(),
                                    player.getUniqueId().toString()));
                            player.sendMessage(MessageTranslator.getTranslatedMessage("block-placed"));
                            MoreCrafting.getInstance().getBlockListFile().addBlock(event.getBlock().getLocation(), player);
                            return;
                        }
                        MoreCrafting.getInstance().getBlockListFile().addBlock(event.getBlock().getLocation(), null);
                        player.sendMessage(MessageTranslator.getTranslatedMessage("block-placed"));

                    }

    }

}
