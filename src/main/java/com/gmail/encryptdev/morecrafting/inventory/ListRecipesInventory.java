package com.gmail.encryptdev.morecrafting.inventory;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EncryptDev
 */
public class ListRecipesInventory extends AbstractInventory {

    private int page;
    private static final List<List<ItemStack>> SUB_LISTS;

    static {
        SUB_LISTS = new ArrayList<>();
    }

    public ListRecipesInventory(int page) {
        super(MessageTranslator.getTranslatedInventoryName("list-recipes").replace("{Page}", String.valueOf(page)), 54, false);
        this.page = page;
    }

    @Override
    public void setup() {

        RecipeManager recipeManager = MoreCrafting.getInstance().getRecipeManager();

        List<ItemStack> allItems = recipeManager.getAllRecipesAsItemStack();

        if (!allItems.isEmpty()) {
            int maxPages = recipeManager.getMaxPages();
            int n = 0;

            if (maxPages > 1) {
                for (int i = 0; i < maxPages; i++) {
                    SUB_LISTS.add(allItems.subList(n, (n + 45 > allItems.size() ? allItems.size() : n + 45)));
                    n += 45;
                }
            } else {
                SUB_LISTS.add(allItems.subList(0, allItems.size()));
            }

            fill();

            for (int i = 0; i < SUB_LISTS.get(page - 1).size(); i++) {
                bukkitInventory.setItem(i, SUB_LISTS.get(page - 1).get(i));
            }

        } else {
            fill();
        }

        bukkitInventory.setItem(45, ItemCreator.getItem(Material.ARROW, MessageTranslator.getTranslatedItemName("page-back")));
        bukkitInventory.setItem(53, ItemCreator.getItem(Material.ARROW, MessageTranslator.getTranslatedItemName("page-next")));

    }


}
