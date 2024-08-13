package tech.samgosden.magestones.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import tech.samgosden.magestones.MageStones;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ItemTagger {
    public void tagIronRelatedItems(DynamicRegistryManager registryManager) {
        Set<Recipe<?>> visitedRecipes = new HashSet<>();
        Set<Item> ironRelatedItems = new HashSet<>();

        for (Recipe<?> recipe : MinecraftClient.getInstance().world.getRecipeManager().values()) {
            if (!visitedRecipes.contains(recipe)) {
                traverseRecipe(recipe, visitedRecipes, ironRelatedItems, registryManager);
            }
        }

        // Add the iron-related items to the tag
        addItemsToTag(ironRelatedItems);
    }

    private void traverseRecipe(Recipe<?> recipe, Set<Recipe<?>> visitedRecipes, Set<Item> ironRelatedItems, DynamicRegistryManager registryManager) {
        visitedRecipes.add(recipe);
        boolean containsIron = false;

        TagKey<Item> IRON_RELATED_TAG = TagKey.of(Registries.ITEM.getKey(), new Identifier(MageStones.MOD_ID, "iron_related"));

        for (Ingredient ingredient : recipe.getIngredients()) {
            for (ItemStack stack : ingredient.getMatchingStacks()) {
                if (stack.isIn(IRON_RELATED_TAG)) {
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
            Item outputItem = recipe.getOutput(registryManager).getItem();
            Identifier outputItemId = Registries.ITEM.getId(outputItem);
            Optional<? extends Recipe<?>> subRecipeOptional = MinecraftClient.getInstance().world.getRecipeManager().get(outputItemId);
            if (subRecipeOptional.isPresent() && !visitedRecipes.contains(subRecipeOptional.get())) {
                traverseRecipe(subRecipeOptional.get(), visitedRecipes, ironRelatedItems, registryManager);
            }
        }
    }

    private void addItemsToTag(Set<Item> items) {
        // Implement logic to add items to the iron_related tag
    }
}
