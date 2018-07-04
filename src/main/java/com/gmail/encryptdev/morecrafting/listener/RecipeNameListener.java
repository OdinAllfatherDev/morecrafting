package com.gmail.encryptdev.morecrafting.listener;

import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeFurnace;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShaped;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShapeless;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Created by EncryptDev
 */
public class RecipeNameListener implements Listener {

    private RecipeManager recipeManager;

    public RecipeNameListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (recipeManager.getNameTable().contains(player, RecipeManager.SHAPED)) {
            event.setCancelled(true);
            String message = event.getMessage();

            if (message.length() > 32 || message.length() <= 0) {
                player.sendMessage(MessageTranslator.getTranslatedMessage("name-error"));
                return;
            }

            if (recipeManager.getRecipeByName(message) != null) {
                player.sendMessage(MessageTranslator.getTranslatedMessage("recipe-already-name-exist").replace("{Name}", message));
                return;
            }

            Map<Integer, ItemStack> shapeItems = recipeManager.getNameTable().get(player, RecipeManager.SHAPED).getShapeItems();
            ItemStack output = recipeManager.getNameTable().get(player, RecipeManager.SHAPED).getOutput();

            recipeManager.addRecipe(new RecipeShaped(message, output).setFinalRecipeMap(shapeItems));
            player.sendMessage(MessageTranslator.getTranslatedMessage("added-recipe").replace("{Name}", message));

            recipeManager.getNameTable().remove(player, RecipeManager.SHAPED);
        } else if (recipeManager.getNameTable().contains(player, RecipeManager.SHAPELESS)) {
            event.setCancelled(true);
            String message = event.getMessage();


            if (recipeManager.getRecipeByName(message) != null) {
                player.sendMessage(MessageTranslator.getTranslatedMessage("recipe-already-name-exist").replace("{Name}", message));
                return;
            }

            if (message.length() > 32 || message.length() <= 0) {
                player.sendMessage(MessageTranslator.getTranslatedMessage("name-error"));
                return;
            }

            List<ItemStack> items = recipeManager.getNameTable().get(player, RecipeManager.SHAPELESS).getItems();
            ItemStack output = recipeManager.getNameTable().get(player, RecipeManager.SHAPELESS).getOutput();

            recipeManager.addRecipe(new RecipeShapeless(message, output).setIngredients(items));
            player.sendMessage(MessageTranslator.getTranslatedMessage("added-recipe").replace("{Name}", message));
            recipeManager.getNameTable().remove(player, RecipeManager.SHAPELESS);
        } else if (recipeManager.getNameTable().contains(player, RecipeManager.FURNACE)) {
            event.setCancelled(true);
            String message = event.getMessage();

            if (recipeManager.getRecipeByName(message) != null) {
                player.sendMessage(MessageTranslator.getTranslatedMessage("recipe-already-name-exist").replace("{Name}", message));
                return;
            }

            if (message.length() > 32 || message.length() <= 0) {
                player.sendMessage(MessageTranslator.getTranslatedMessage("name-error"));
                return;
            }

            ItemStack input = recipeManager.getNameTable().get(player, RecipeManager.FURNACE).getInput();
            ItemStack output = recipeManager.getNameTable().get(player, RecipeManager.FURNACE).getOutput();
            float experience = recipeManager.getNameTable().get(player, RecipeManager.FURNACE).getExperince();

            recipeManager.addRecipe(new RecipeFurnace(message, output).setInput(input).setExperience(experience));
            player.sendMessage(MessageTranslator.getTranslatedMessage("added-recipe").replace("{Name}", message));
            recipeManager.getNameTable().remove(player, RecipeManager.FURNACE);
        }

    }

}
