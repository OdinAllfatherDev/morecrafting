package com.gmail.encryptdev.morecrafting.recipe;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.json.JsonLoader;
import com.gmail.encryptdev.morecrafting.recipe.recipes.ARecipe;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeFurnace;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShaped;
import com.gmail.encryptdev.morecrafting.recipe.recipes.RecipeShapeless;
import com.gmail.encryptdev.morecrafting.util.HelpStorage;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.Log;
import com.gmail.encryptdev.morecrafting.util.MessageTranslator;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.management.InstanceNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by EncryptDev
 * <p>
 * The RecipeManager class, manage the recipes. Add recipes, or remove recipes. Find shapes or scan shapes.
 */
public class RecipeManager {

    public static final int SHAPED = 1;
    public static final int SHAPELESS = 2;
    public static final int FURNACE = 3;

    private List<RecipeFurnace> furnaceRecipes;
    private List<RecipeShaped> shapedRecipes;
    private List<RecipeShapeless> shapelessRecipes;
    private JsonLoader jsonLoader;
    private Map<Material, Short> itemMatData;
    private File recipeFile;
    private FileConfiguration configuration;
    private RecipeScanner recipeScanner;
    private Table<Player, Integer, HelpStorage> nameTable;
    private Map<Player, HelpStorage> tempHelpStorage;

    public RecipeManager(JsonLoader jsonLoader) {
        this.jsonLoader = jsonLoader;
        this.itemMatData = new HashMap<>();
        this.furnaceRecipes = new LinkedList<>();
        this.shapedRecipes = new LinkedList<>();
        this.shapelessRecipes = new LinkedList<>();
        this.recipeScanner = new RecipeScanner();
        this.tempHelpStorage = new HashMap<>();
        this.nameTable = HashBasedTable.create();
        this.recipeFile = new File(MoreCrafting.getInstance().getDataFolder(), "recipes.yml");
        if (!recipeFile.exists())
            try {
                recipeFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        this.configuration = YamlConfiguration.loadConfiguration(recipeFile);

    }

    /**
     * Load all the stuff, here in the class
     */
    public void init() {
        if (!this.configuration.contains("furnaceRecipes")) {
            this.configuration.set("furnaceRecipes", furnaceRecipes);
            this.configuration.set("shapedRecipes", shapedRecipes);
            this.configuration.set("shapelessRecipes", shapelessRecipes);
            this.saveFile();
        }
        loadCustomWorkbenchRecipe();
        loadDefaultRecipesIntoMap();
        loadRecipes();
    }

    /**
     * Add a recipe
     *
     * @param aRecipe - the recipe
     */
    public void addRecipe(ARecipe aRecipe) {
        if (aRecipe instanceof RecipeFurnace) {
            RecipeFurnace recipeFurnace = (RecipeFurnace) aRecipe;
            if (furnaceRecipes.contains(recipeFurnace))
                return;
            furnaceRecipes.add(recipeFurnace);
            this.configuration.set("furnaceRecipes", furnaceRecipes);
            this.saveFile();

            FurnaceRecipe recipe = new FurnaceRecipe(recipeFurnace.getOutput(), recipeFurnace.getInput().getType());
            Bukkit.addRecipe(recipe);

            Log.info("Added furnace recipe [" + aRecipe.getName() + "]");
        } else if (aRecipe instanceof RecipeShapeless) {
            RecipeShapeless recipeShapeless = (RecipeShapeless) aRecipe;
            if (shapelessRecipes.contains(recipeShapeless))
                return;
            shapelessRecipes.add(recipeShapeless);
            this.configuration.set("shapelessRecipes", shapelessRecipes);
            this.saveFile();
            Log.info("Added shapeless recipe [" + aRecipe.getName() + "]");
        } else if (aRecipe instanceof RecipeShaped) {
            RecipeShaped recipeShaped = (RecipeShaped) aRecipe;
            if (shapedRecipes.contains(recipeShaped))
                return;

            shapedRecipes.add(recipeShaped);
            this.configuration.set("shapedRecipes", shapedRecipes);
            this.saveFile();
            Log.info("Added shaped recipe [" + aRecipe.getName() + "]");
        } else
            Log.throwError("Can not add custom recipe with name: " + aRecipe.getName() + " it is not a recipe instance from the plugin",
                    new InstanceNotFoundException());

    }

    public boolean deleteRecipe(String name) {
        if (getRecipeByName(name) == null)
            return false;

        ARecipe recipe = getRecipeByName(name);
        if (recipe instanceof RecipeFurnace) {
            furnaceRecipes.remove(recipe);
            this.configuration.set("furnaceRecipes", shapedRecipes);
            this.saveFile();
        } else if (recipe instanceof RecipeShapeless) {
            shapelessRecipes.remove(recipe);
            this.configuration.set("shapelessRecipes", shapedRecipes);
            this.saveFile();
        } else if (recipe instanceof RecipeShaped) {
            shapedRecipes.remove(recipe);
            this.configuration.set("shapedRecipes", shapedRecipes);
            this.saveFile();
        }

        Log.info("Removed recipe [" + name + "]");

        return true;
    }

    public RecipeShaped findShapedRecipe(Inventory inventory) {
        for (RecipeShaped shapedRecipe : shapedRecipes)
            if (shapedRecipe.canCraft(inventory))
                return shapedRecipe;

        return null;
    }

    public RecipeShapeless findShapelessRecipe(Inventory inventory) {

        for (RecipeShapeless recipeShapeless : shapelessRecipes)
            if (recipeShapeless.canCraft(inventory))
                return recipeShapeless;

        return null;
    }

    public RecipeFurnace findFurnaceRecipe(ItemStack output, ItemStack input) {

        for (RecipeFurnace recipeFurnace : furnaceRecipes)
            if (recipeFurnace.getOutput().equals(output) && recipeFurnace.getInput().equals(input))
                return recipeFurnace;

        return null;
    }

    public ARecipe getRecipeByName(String name) {
        for (RecipeShaped shaped : shapedRecipes)
            if (shaped.getName().equals(name))
                return shaped;

        for (RecipeShapeless shapeless : shapelessRecipes)
            if (shapeless.getName().equals(name))
                return shapeless;

        for (RecipeFurnace furnace : furnaceRecipes)
            if (furnace.getName().equals(name))
                return furnace;

        return null;
    }

    private void loadRecipes() {
        Log.info("Load recipes...");

        this.furnaceRecipes.addAll((List<RecipeFurnace>) configuration.get("furnaceRecipes"));
        this.shapedRecipes.addAll((List<RecipeShaped>) configuration.get("shapedRecipes"));
        this.shapelessRecipes.addAll((List<RecipeShapeless>) configuration.get("shapelessRecipes"));

        for (RecipeFurnace recipeFurnace : furnaceRecipes) {
            FurnaceRecipe furnacer = new FurnaceRecipe(recipeFurnace.getOutput(), recipeFurnace.getInput().getType());
            Bukkit.addRecipe(furnacer);
        }

    }

    private void saveFile() {
        try {
            this.configuration.save(this.recipeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the custom workbench recipe
     */
    private void loadCustomWorkbenchRecipe() {
        Log.info("Load custom workbench recipe...");
        JSONObject customRecipeObj = jsonLoader.getRecipeSettingsFile().getJsonObject("custom-workbench");
        JSONObject shape = (JSONObject) customRecipeObj.get("shape");
        String[] rows = new String[]{(String) shape.get("row-1"), (String) shape.get("row-2"), (String) shape.get("row-3")};
        JSONArray ingredients = (JSONArray) shape.get("ingredients");
        Map<Character, Material> ingredientsMap = new HashMap<>();

        Iterator<String> ingredientsIterator = ingredients.iterator();
        while (ingredientsIterator.hasNext()) {
            String ingredient = ingredientsIterator.next();
            String[] data = ingredient.split(":");
            if (data.length != 2)
                Log.throwError("Can not load custom workbench recipe, ingredient data has not the right format. Right format: C:MATERIAL" +
                        " [C = Character, MATERIAL = The item material]", new RuntimeException());

            Character character = data[0].toCharArray()[0];
            Material material = Material.getMaterial(data[1]);
            if (material == null)
                Log.throwError("Can not load custom workbench recipe, the material [" + data[1] + "] doesn't exist", new NullPointerException());

            ingredientsMap.put(character, material);
        }

        if (ingredientsMap.isEmpty())
            Log.throwError("Can not load custom workbench recipe, ingredient have a size of 0", new RuntimeException());


        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("custom_workbench"), ItemCreator.getItem(Material.WORKBENCH,
                ChatColor.translateAlternateColorCodes('&', (String) customRecipeObj.get("name"))));
        recipe.shape(rows);
        for (Character c : ingredientsMap.keySet())
            recipe.setIngredient(c, ingredientsMap.get(c));

        Bukkit.addRecipe(recipe);
    }

    private void loadDefaultRecipesIntoMap() {

        Log.info("Remove recipes...");

        JSONArray array = jsonLoader.getRecipeSettingsFile().getJsonArray("remove-recipes");
        Iterator<String> iterator = array.iterator();

        while (iterator.hasNext()) {
            String string = iterator.next();

            String[] data = string.split(":");
            if (data.length != 2)
                Log.throwError("Can not remove default recipe. Data length is '" + data.length + "', but it must be 2. Use this format: MATERIAL:DATA",
                        new RuntimeException());

            Material material = Material.getMaterial(data[0]);
            if (material == null) {
                Log.warning("Can not remove default recipe. Material '" + data[0] + "' not found");
                continue;
            }

            short durData = Short.valueOf(data[1]);
            itemMatData.put(material, durData);

        }

    }

    public List<ItemStack> getAllRecipesAsItemStack() {
        List<ItemStack> allItems = new ArrayList<>();
        for (RecipeShaped shaped : shapedRecipes)
            allItems.add(ItemCreator.getItem(Material.PAPER, "§5§l" + shaped.getName(), 1, (byte) 0,
                    Arrays.asList(MessageTranslator.getTranslatedMessageWithoutPrefix("list-item-output").replace("{Item}",
                            shaped.getOutput().hasItemMeta() ? shaped.getOutput().getItemMeta().getDisplayName() :
                                    MoreCrafting.makeEnumNormal(shaped.getOutput().getType())))));

        for (RecipeShapeless shapeless : shapelessRecipes)
            allItems.add(ItemCreator.getItem(Material.MAP, "§5§l" + shapeless.getName(), 1, (byte) 0,
                    Arrays.asList(MessageTranslator.getTranslatedMessageWithoutPrefix("list-item-output").replace("{Item}",
                            shapeless.getOutput().hasItemMeta() ? shapeless.getOutput().getItemMeta().getDisplayName() :
                                    MoreCrafting.makeEnumNormal(shapeless.getOutput().getType())))));

        for (RecipeFurnace furnace : furnaceRecipes)
            allItems.add(ItemCreator.getItem(Material.FURNACE, "§5§l" + furnace.getName(), 1, (byte) 0,
                    Arrays.asList(MessageTranslator.getTranslatedMessageWithoutPrefix("list-item-output").replace("{Item}",
                            furnace.getOutput().hasItemMeta() ? furnace.getOutput().getItemMeta().getDisplayName() :
                                    MoreCrafting.makeEnumNormal(furnace.getOutput().getType())))));
        return allItems;
    }

    public int getMaxPages() {
        int itemAmounts = getAllRecipesAsItemStack().size();
        int page = 1;
        for (; itemAmounts > 45; itemAmounts -= 45) page++;
        return page;
    }

    public Map<Player, HelpStorage> getTempHelpStorage() {
        return tempHelpStorage;
    }


    public RecipeScanner getRecipeScanner() {
        return recipeScanner;
    }

    public Table<Player, Integer, HelpStorage> getNameTable() {
        return nameTable;
    }

    public Map<Material, Short> getItemMatData() {
        return itemMatData;
    }

}