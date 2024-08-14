package tech.samgosden.magestones.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.Registries;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import tech.samgosden.magestones.MageStones;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemTagger {
    RecipeManager recipeManager;
    Set<Item> ironRelatedItems = new HashSet<>();
    Set<Item> visitedItems = new HashSet<>();
    List<Item> recentlyTraversed = new java.util.ArrayList<>(List.of());
    /**
     * This function is used to tag items that are related to iron in the game.
     * It traverses through all the recipes, checks if they contain any item tagged as 'iron_related',
     * and if so, adds the output item of that recipe to the 'iron_related' tag.
     *
     * @param server           The Minecraft server instance.
     * @param registryManager  The DynamicRegistryManager instance that manages the game's registries.
     */
    public void tagIronRelatedItems(MinecraftServer server, DynamicRegistryManager registryManager) {
        Set<Recipe<?>> visitedRecipes = new HashSet<>();
        recipeManager = server.getRecipeManager();
        ironRelatedItems.add(Registries.ITEM.get(new Identifier("minecraft", "iron_ingot")));

        for (Recipe<?> recipe : recipeManager.values()) {
            if (!visitedRecipes.contains(recipe)) {
                traverseRecipe(recipe, visitedRecipes, registryManager);
            }
        }

        // Add the iron-related items to the tag
        addItemsToTag(ironRelatedItems);
    }

    private void traverseRecipe(Recipe<?> recipe, Set<Recipe<?>> visitedRecipes, DynamicRegistryManager registryManager) {
        visitedRecipes.add(recipe);
        Item out = recipe.getOutput(registryManager).getItem();
        //if recently traversed is full remove the first item that was added
        if(recentlyTraversed.size()==20){
            recentlyTraversed.remove(0);
        }
        recentlyTraversed.add(out);
        boolean allFound = true;
        //if visited recipes includes all the recipies add the item to the set
        for(Recipe<?> Recpies : recipeManager.values()) {
            //ensure that all the recipes for out are contained in visitedRecipes
            if (Recpies.getOutput(registryManager).getItem()==out && !visitedRecipes.contains(Recpies)){
                allFound=false;
                break;
            }
        }
        if(allFound){
            visitedItems.add(out);
        }
        boolean containsIron = false;


        for (Ingredient ingredient : recipe.getIngredients()) {
            for (ItemStack stack : ingredient.getMatchingStacks()) {
                if (ironRelatedItems.contains(stack.getItem())) {
                    containsIron = true;
                    break;
                }
            }
            if (containsIron) {
                break;
            }
        }

        if (containsIron) {
            ironRelatedItems.add(recipe.getOutput(registryManager).getItem());
        } else {
            for (Ingredient ingredient : recipe.getIngredients()) {
                //get all valid items for this ingredient
                List<Item> ingredientItems = new java.util.ArrayList<>(List.of());
                for (ItemStack stack : ingredient.getMatchingStacks()) {
                    Item item = stack.getItem();
                    if(item!=null && !visitedItems.contains(item) && item != out && !recentlyTraversed.contains(item)) {
                        ingredientItems.add(item);
                    }
                }
                //for this Ingredient get every recipe
                for (Recipe<?> subRecipes : recipeManager.values()) {
                    if(ingredientItems.contains(subRecipes.getOutput(registryManager).getItem()))
                    {
                        traverseRecipe(subRecipes, visitedRecipes, registryManager);
                    }
                }
            }
        }
    }

    private void addItemsToTag(Set<Item> items) {
        //print the name of all the items
        for (Item item : items) {

        }
    }
}
