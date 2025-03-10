package com.example.disassemblytable.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeUtil {
    public static List<ItemStack> getDisassemblyIngredients(Level level, ItemStack stack) {
        List<ItemStack> ingredients = new ArrayList<>();
        
        if (stack.isEmpty()) {
            return ingredients;
        }

        // Get all crafting recipes
        List<CraftingRecipe> recipes = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
        
        // Find a recipe that produces the input item
        Optional<CraftingRecipe> matchingRecipe = recipes.stream()
                .filter(recipe -> {
                    ItemStack result = recipe.getResultItem();
                    return ItemStack.isSameItem(result, stack);
                })
                .findFirst();
        
        if (matchingRecipe.isPresent()) {
            CraftingRecipe recipe = matchingRecipe.get();
            
            // Get the ingredients from the recipe
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (!ingredient.isEmpty()) {
                    ItemStack[] matchingStacks = ingredient.getItems();
                    if (matchingStacks.length > 0) {
                        // Use the first matching stack
                        ingredients.add(matchingStacks[0].copy());
                    }
                }
            }
        }
        
        return ingredients;
    }
}

