package com.gmail.encryptdev.morecrafting.event;

import com.gmail.encryptdev.morecrafting.recipe.recipes.ARecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by EncryptDev
 */
public class APICreateRecipeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private ARecipe recipe;

    public APICreateRecipeEvent(Player player, ARecipe recipe) {
        this.player = player;
        this.recipe = recipe;
    }

    public ARecipe getRecipe() {
        return recipe;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
