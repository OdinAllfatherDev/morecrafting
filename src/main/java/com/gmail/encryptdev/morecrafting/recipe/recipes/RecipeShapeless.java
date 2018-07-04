package com.gmail.encryptdev.morecrafting.recipe.recipes;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.util.ListUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by EncryptDev
 */
public class RecipeShapeless extends ARecipe {

    private List<ItemStack> ingredients;

    public RecipeShapeless(String name, ItemStack output) {
        super(name, output);
        this.ingredients = new LinkedList<>();
    }

    @Override
    public boolean canCraft(Inventory inventory) {

        List<ItemStack> items = MoreCrafting.getInstance().getRecipeManager().getRecipeScanner().scanShapelessShape(inventory);

        int counter = 0;

        List<ItemStack> sortedItems = ListUtil.sort(items);
        List<ItemStack> sortedIngredients = ListUtil.sort(ingredients);

        if(sortedItems.size() != sortedIngredients.size())
            return false;

        for (int i = 0; i < sortedIngredients.size(); i++) {
            ItemStack ingre = sortedIngredients.get(i);
            ItemStack item = sortedItems.get(i);

            if (item.getType() == ingre.getType() && item.getAmount() >= ingre.getAmount() && item.getDurability() == ingre.getDurability())
                counter += 1;
        }

        return counter == ingredients.size();
    }

    public RecipeShapeless setIngredients(List<ItemStack> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    @Override
    public void removeItems(Inventory inventory) {

        List<ItemStack> items = MoreCrafting.getInstance().getRecipeManager().getRecipeScanner().scanShapelessShape(inventory);

        if (ingredients.size() != items.size())
            return;

        for (int i = 0; i < ingredients.size(); i++) {
            ItemStack fromIngre = ingredients.get(i);
            ItemStack fromInv = items.get(i);

            if (fromInv == null)
                continue;

            int newAmount = fromInv.getAmount() - fromIngre.getAmount();
            fromInv.setAmount(newAmount);

        }

    }

    public RecipeShapeless(Map<String, Object> map) {
        super((String) map.get("name"), (ItemStack) map.get("output"));
        this.ingredients = (List<ItemStack>) map.get("ingredients");
    }

    public RecipeShapeless addIngredient(ItemStack itemStack) {
        if (ingredients.contains(itemStack))
            return this;
        ingredients.add(itemStack);
        return this;
    }

    public RecipeShapeless removeIngredient(ItemStack itemStack) {
        if (!ingredients.contains(itemStack))
            return this;
        ingredients.remove(itemStack);
        return this;
    }

    public List<ItemStack> getIngredients() {
        return ingredients;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("output", getOutput());
        map.put("ingredients", ingredients);
        return map;
    }

    public static RecipeShapeless deserialize(Map<String, Object> map) {
        return new RecipeShapeless(map);
    }
}
