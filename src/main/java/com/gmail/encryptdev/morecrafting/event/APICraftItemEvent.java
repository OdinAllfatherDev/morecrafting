package com.gmail.encryptdev.morecrafting.event;

import com.gmail.encryptdev.morecrafting.recipe.recipes.ARecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by EncryptDev
 */
public class APICraftItemEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private ARecipe recipe;

    public APICraftItemEvent(Player player, ARecipe recipe) {
        this.player = player;
        this.recipe = recipe;
    }

    public Player getPlayer() {
        return player;
    }

    public ARecipe getRecipe() {
        return recipe;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
