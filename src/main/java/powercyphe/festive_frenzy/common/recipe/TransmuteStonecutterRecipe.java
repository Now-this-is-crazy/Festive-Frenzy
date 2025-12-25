package powercyphe.festive_frenzy.common.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import powercyphe.festive_frenzy.common.registry.FFRecipes;

public class TransmuteStonecutterRecipe extends StonecutterRecipe {
    public TransmuteStonecutterRecipe(String string, Ingredient ingredient, ItemStack itemStack) {
        super(string, ingredient, itemStack);
    }

    @Override
    public ItemStack assemble(SingleRecipeInput recipeInput, HolderLookup.Provider provider) {
        return recipeInput.item().transmuteCopy(this.result().getItem(), 1);
    }

    @Override
    public RecipeSerializer<StonecutterRecipe> getSerializer() {
        return FFRecipes.TRANSMUTE_STONECUTTING_RECIPE;
    }

    @Override
    public RecipeType<StonecutterRecipe> getType() {
        return RecipeType.STONECUTTING;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.STONECUTTER;
    }
}
