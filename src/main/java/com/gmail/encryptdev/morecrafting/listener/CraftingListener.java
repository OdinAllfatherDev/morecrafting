package com.gmail.encryptdev.morecrafting.listener;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.event.APICraftItemEvent;
import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShaped;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShapeless;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EncryptDev
 */
public class CraftingListener implements Listener {

    private RecipeManager recipeManager;
    private String workbenchName;

    public CraftingListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
        this.workbenchName = ChatColor.translateAlternateColorCodes('&',
                (String) MoreCrafting.getInstance().getJsonLoader().getRecipeSettingsFile().getJsonObject("custom-workbench").get("name"));
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(workbenchName)) {
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() != null)
                if (event.getCurrentItem().hasItemMeta()) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName() != null)
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§0"))
                            event.setCancelled(true);
                        else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageTranslator.getTranslatedItemName("craft-item"))) {
                            event.setCancelled(true);

                            RecipeShaped shaped = recipeManager.findShapedRecipe(event.getInventory());
                            if (shaped != null && shaped.canCraft(event.getInventory())) {
                                ItemStack output = shaped.getOutput().clone();
                                ItemStack invOutput = event.getInventory().getItem(53);

                                if (invOutput == null) {
                                    event.getInventory().setItem(53, output);
                                    shaped.removeItems(event.getInventory());
                                    player.sendMessage(MessageTranslator.getTranslatedMessage("crafted-item").replace("{Name}", output.hasItemMeta() ?
                                            output.getItemMeta().getDisplayName() : MoreCrafting.makeEnumNormal(output.getType())));
                                } else {

                                    int newAmount = invOutput.getAmount() + output.getAmount();
                                    output.setAmount(newAmount);
                                    event.getInventory().setItem(53, output);
                                    shaped.removeItems(event.getInventory());
                                    player.sendMessage(MessageTranslator.getTranslatedMessage("crafted-item").replace("{Name}", output.hasItemMeta() ?
                                            output.getItemMeta().getDisplayName() : MoreCrafting.makeEnumNormal(output.getType())));
                                }

                                Bukkit.getPluginManager().callEvent(new APICraftItemEvent(player, shaped));
                            }

                            RecipeShapeless shapeless = recipeManager.findShapelessRecipe(event.getInventory());
                            if (shapeless != null && shapeless.canCraft(event.getInventory())) {
                                ItemStack output = shapeless.getOutput().clone();
                                ItemStack invOutput = event.getInventory().getItem(53);

                                if (invOutput == null) {
                                    event.getInventory().setItem(53, output);
                                    shapeless.removeItems(event.getInventory());
                                    player.sendMessage(MessageTranslator.getTranslatedMessage("crafted-item").replace("{Name}", output.hasItemMeta() ?
                                            output.getItemMeta().getDisplayName() : MoreCrafting.makeEnumNormal(output.getType())));
                                } else {
                                    int newAmount = invOutput.getAmount() + output.getAmount();
                                    output.setAmount(newAmount);
                                    event.getInventory().setItem(53, output);
                                    shapeless.removeItems(event.getInventory());
                                    player.sendMessage(MessageTranslator.getTranslatedMessage("crafted-item").replace("{Name}", output.hasItemMeta() ?
                                            output.getItemMeta().getDisplayName() : MoreCrafting.makeEnumNormal(output.getType())));
                                }
                                Bukkit.getPluginManager().callEvent(new APICraftItemEvent(player, shapeless));

                            }

                        }
                }
        }
    }

}
