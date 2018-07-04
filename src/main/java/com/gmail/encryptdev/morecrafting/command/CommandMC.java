package com.gmail.encryptdev.morecrafting.command;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.inventory.AbstractInventory;
import com.gmail.encryptdev.morecrafting.inventory.ChooseRecipeInventory;
import com.gmail.encryptdev.morecrafting.inventory.ListRecipesInventory;
import com.gmail.encryptdev.morecrafting.inventory.ShowRecipeInventory;
import com.gmail.encryptdev.morecrafting.util.Log;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by EncryptDev
 */
public class CommandMC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player))
            Log.warning("You are not a player");
        else {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                if (hasStarPermission(player)) {
                    if (hasStarPermission(player)) {
                        player.sendMessage("§5§lMoreCrafting v" + MoreCrafting.getInstance().getDescription().getVersion() + " by EncryptDev");
                        player.sendMessage("§6§lMoreCrafting >> §aHelp? Join my discord: https://discord.gg/NUPDFR7");
                        player.sendMessage("§6§lMoreCrafting >> §aCommands:");
                        player.sendMessage("§6§lMoreCrafting >> §a/mc create - open the gui, to create a recipe");
                        player.sendMessage("§6§lMoreCrafting >> §a/mc list - list all recipes in a gui");
                        player.sendMessage("§6§lMoreCrafting >> §a/mc delete <name> - delete a recipe");
                        player.sendMessage("§6§lMoreCrafting >> §a/mc show <name> - show the recipe");
                    }
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("create") && player.hasPermission("morecrafting.create")) {
                    AbstractInventory.openInventory(player, new ChooseRecipeInventory());
                } else if (args[0].equalsIgnoreCase("list") && player.hasPermission("morecrafting.list")) {
                    AbstractInventory.openInventory(player, new ListRecipesInventory(1));
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("delete") && player.hasPermission("morecrafting.delete")) {
                    String name = args[1];

                    if (MoreCrafting.getInstance().getRecipeManager().getRecipeByName(name) == null) {
                        player.sendMessage(MessageTranslator.getTranslatedMessage("recipe-not-exist").replace("{Name}", name));
                        return true;
                    }

                    if (MoreCrafting.getInstance().getRecipeManager().deleteRecipe(name)) {
                        player.sendMessage(MessageTranslator.getTranslatedMessage("delete-successful").replace("{Name}", name));
                    } else {
                        player.sendMessage(MessageTranslator.getTranslatedMessage("delete-not-successful").replace("{Name}", name));
                    }
                } else if (args[0].equalsIgnoreCase("show") && player.hasPermission("morecrafting.show")) {
                    String name = args[1];

                    if (MoreCrafting.getInstance().getRecipeManager().getRecipeByName(name) == null) {
                        player.sendMessage(MessageTranslator.getTranslatedMessage("recipe-not-exist").replace("{Name}", name));
                        return true;
                    }

                    AbstractInventory.openInventory(player, new ShowRecipeInventory(ChatColor.stripColor(name)));
                }
            }

        }
        return true;
    }

    private boolean hasStarPermission(Player player) {
        return player.hasPermission("morecrafting.*") || player.isOp();
    }
}
