package powercyphe.festive_frenzy.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import powercyphe.festive_frenzy.registry.ModRecipes;

public class PresentStonecuttingRecipe extends StonecuttingRecipe {
    public PresentStonecuttingRecipe(String group, Ingredient input, ItemStack output) {
        super(group, input, output);
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        NbtCompound nbt = inventory.getStack(0).getOrCreateNbt();
        ItemStack stack = this.result.copy();
        stack.setNbt(nbt);
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.PRESENT_STONECUTTING;
    }

    public static class Serializer<T extends CuttingRecipe> extends CuttingRecipe.Serializer<T> {
        public Serializer(RecipeFactory<T> recipeFactory) {
            super(recipeFactory);
        }
    }
}
