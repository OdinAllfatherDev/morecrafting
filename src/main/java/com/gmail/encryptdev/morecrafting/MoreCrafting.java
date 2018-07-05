package com.gmail.encryptdev.morecrafting;

import com.gmail.encryptdev.morecrafting.command.CommandMC;
import com.gmail.encryptdev.morecrafting.json.JsonLoader;
import com.gmail.encryptdev.morecrafting.listener.*;
import com.gmail.encryptdev.morecrafting.listener.inventory.*;
import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeFurnace;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShaped;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShapeless;
import com.gmail.encryptdev.morecrafting.util.BlockListFile;
import com.gmail.encryptdev.morecrafting.util.Log;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by EncryptDev
 */
public class MoreCrafting extends JavaPlugin {

    public static final String CRAFTING_META_DATA = "more_crafting_crafting_block";
    public static final String BLOCK_OWNER_META_DATA = "more_crafting_block_owner";

    private static MoreCrafting instance;

    private JsonLoader jsonLoader;
    private RecipeManager recipeManager;
    private BlockListFile blockListFile;
    private UpdateChecker updateChecker;

    @Override
    public void onEnable() {
        instance = this;

        ConfigurationSerialization.registerClass(RecipeShapeless.class);
        ConfigurationSerialization.registerClass(RecipeShaped.class);
        ConfigurationSerialization.registerClass(RecipeFurnace.class);

        Log.info("Load files...");
        if (!new File(getDataFolder(), "messages.json").exists())
            saveResource("messages.json", false);
        if (!new File(getDataFolder(), "recipe-settings.json").exists())
            saveResource("recipe-settings.json", false);
        this.saveDefaultConfig();

        this.jsonLoader = new JsonLoader();
        this.jsonLoader.load();

        this.recipeManager = new RecipeManager(this.jsonLoader);
        this.recipeManager.init();

        this.blockListFile = new BlockListFile();
        this.blockListFile.loadAll();

        this.updateChecker = new UpdateChecker();
        this.updateChecker.check();

        this.loadListener();

        if(this.updateChecker.isAvailable()) {
            Bukkit.getConsoleSender().sendMessage("§6§lMoreCrafting >> §aA update is available");
        }

        getCommand("mc").setExecutor(new CommandMC());

        Log.info("Plugin enabled");
        Log.info("Developer: EncryptDev");
        Log.info("Version: " + getDescription().getVersion());


    }

    private void loadListener() {
        getServer().getPluginManager().registerEvents(updateChecker, this);
        getServer().getPluginManager().registerEvents(new RemovedRecipeListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new WorkbenchPlaceListener(jsonLoader), this);
        getServer().getPluginManager().registerEvents(new RecipeNameListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new WorkbenchInteractListener(), this);
        getServer().getPluginManager().registerEvents(new CreateShapedRecipeInventoryListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new ChooseRecipeInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new CraftingListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new CreateShapelessRecipeInventoryListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new CreateFurnaceRecipeInventoryListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new CounterInventoryListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new RecipeListInventoryListener(recipeManager), this);
        getServer().getPluginManager().registerEvents(new ShowRecipeListener(), this);
        getServer().getPluginManager().registerEvents(new WorkbenchBreakListener(), this);
    }

    public JsonLoader getJsonLoader() {
        return jsonLoader;
    }

    public static MoreCrafting getInstance() {
        return instance;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public BlockListFile getBlockListFile() {
        return blockListFile;
    }

    public static String makeEnumNormal(Enum enumValue) {
        String etvStr = enumValue.toString();
        String lowerCase = etvStr.toLowerCase();
        String result;

        if (lowerCase.contains("_")) {
            char firstIndex = lowerCase.charAt(0);
            char afterSplit = lowerCase.charAt(lowerCase.indexOf("_") + 1);

            String restLeft = lowerCase.substring(1, lowerCase.indexOf("_"));
            String restRight = lowerCase.substring(lowerCase.indexOf("_") + 2, lowerCase.length());
            result = String.valueOf(firstIndex).toUpperCase() + restLeft + " " + String.valueOf(afterSplit).toUpperCase() + restRight;
        } else {
            char firstIndex = lowerCase.charAt(0);
            String rest = lowerCase.substring(1, lowerCase.length());
            result = String.valueOf(firstIndex).toUpperCase() + rest;
        }
        return result;
    }
}
