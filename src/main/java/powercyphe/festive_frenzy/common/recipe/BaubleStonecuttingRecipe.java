package powercyphe.festive_frenzy.common.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import powercyphe.festive_frenzy.common.registry.ModRecipes;

public class BaubleStonecuttingRecipe extends StonecuttingRecipe {
    public BaubleStonecuttingRecipe(String group, Ingredient input, ItemStack output) {
        super(group, input, output);
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput inventory, RegistryWrapper.WrapperLookup registryLookup) {
        ItemStack stack = this.result.copy();
        stack.applyComponentsFrom(inventory.getStackInSlot(0).getComponents());
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BAUBLE_STONECUTTING;
    }

    public static class Serializer<T extends CuttingRecipe> extends CuttingRecipe.Serializer<T> {
        public Serializer(RecipeFactory<T> recipeFactory) {
            super(recipeFactory);
        }
    }
}
