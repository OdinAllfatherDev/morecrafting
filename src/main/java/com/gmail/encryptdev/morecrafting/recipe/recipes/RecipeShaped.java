package com.gmail.encryptdev.morecrafting.recipe.recipes;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.recipe.RecipeManager;
import com.gmail.encryptdev.morecrafting.util.ItemCreator;
import com.gmail.encryptdev.morecrafting.util.Log;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EncryptDev
 */
public class RecipeShaped extends ARecipe {

    private String[] rows;
    private Map<Character, ItemStack> ingredients;
    private Map<Integer, ItemStack> finalRecipeMap;

    public RecipeShaped(String name, ItemStack output) {
        super(name, output);
        this.rows = new String[5];
        this.ingredients = new HashMap<>();
        this.finalRecipeMap = new HashMap<>();
    }


    public RecipeShaped(Map<String, Object> map) {
        super((String) map.get("name"), (ItemStack) map.get("output"));
        this.ingredients = (Map<Character, ItemStack>) map.get("ingredients");
        this.finalRecipeMap = (Map<Integer, ItemStack>) map.get("finalRecipeMap");
    }

    @Override
    public boolean canCraft(Inventory inventory) {

        Map<Integer, ItemStack> other = MoreCrafting.getInstance().getRecipeManager().getRecipeScanner().scanShapedShape(inventory);

        if (other.size() != finalRecipeMap.size())
            return false;

        int index = 0;

        for (int key : finalRecipeMap.keySet()) {
            ItemStack needed = finalRecipeMap.get(key);

            ItemStack inInv = other.get(key);

            if(needed == null || inInv == null)
                continue;

            if (needed.getType() == inInv.getType() && inInv.getAmount() >= needed.getAmount() && inInv.getDurability() == needed.getDurability())
                index += 1;

        }
        return index == finalRecipeMap.size();
    }

    @Override
    public void removeItems(Inventory inventory) {

        RecipeManager recipeManager = MoreCrafting.getInstance().getRecipeManager();
        Map<Integer, ItemStack> shapeItems = recipeManager.getRecipeScanner().scanShapedShape(inventory);

        if (shapeItems.size() != finalRecipeMap.size())
            return;


        for (int key : shapeItems.keySet()) {
            ItemStack fromInv = shapeItems.get(key);
            ItemStack fromFinal = finalRecipeMap.get(key);
            if (fromFinal == null)
                continue;

            int newAmount = fromInv.getAmount() - fromFinal.getAmount();
            if (newAmount == 0) {
                inventory.setItem(key, null);
            } else {
                inventory.setItem(key, ItemCreator.copyItemWithNewAmount(fromInv, newAmount));
            }
        }
    }

    /**
     * Set the shape as array
     * The shape format is "01234" - Every number, can a character. (for nothing use space)
     *
     * @param shape
     * @return
     */
    public RecipeShaped shape(String... shape) {
        if (shape.length > 5)
            Log.throwError("shape length is " + shape.length + ", it must be 5", new IndexOutOfBoundsException());
        for (String s : shape)
            if (s.length() != 5)
                Log.throwError("shape: " + s + " must be a length of 5 characters", new IndexOutOfBoundsException());
        rows = shape;
        return this;
    }

    public RecipeShaped setIngredient(char c, ItemStack itemStack) {
        ingredients.put(c, itemStack);
        return this;
    }

    public RecipeShaped setFinalRecipeMap(Map<Integer, ItemStack> finalRecipeMap) {
        this.finalRecipeMap = finalRecipeMap;
        return this;
    }

    public RecipeShaped create() {
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            if (row == null)
                continue;
            char[] chars = row.toCharArray();

            for (int j = 0; j < chars.length; j++) {
                char c = chars[j];
                if (c == ' ')
                    continue;
                if (ingredients.get(c) == null)
                    Log.throwError("character '" + c + "' doesn't exist in the ingrdient map", new NullPointerException());

                if (i == 0)
                    finalRecipeMap.put(j, ingredients.get(c));

                if (i == 1)
                    finalRecipeMap.put(j + 9, ingredients.get(c));

                if (i == 2)
                    finalRecipeMap.put(j + 18, ingredients.get(c));

                if (i == 3)
                    finalRecipeMap.put(j + 27, ingredients.get(c));

                if (i == 4)
                    finalRecipeMap.put(j + 36, ingredients.get(c));

            }

        }
        return this;
    }

    public Map<Integer, ItemStack> getFinalRecipeMap() {
        return finalRecipeMap;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("output", getOutput());
        map.put("ingredients", ingredients);
        map.put("finalRecipeMap", finalRecipeMap);
        return map;
    }

    public static RecipeShaped deserialize(Map<String, Object> map) {
        return new RecipeShaped(map);
    }
}
