package powercyphe.festive_frenzy.common.recipe;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.common.item.BaubleBlockItem;
import powercyphe.festive_frenzy.common.registry.ModRecipes;
import powercyphe.festive_frenzy.common.registry.ModTags;

public class BaubleRecolorRecipe extends SpecialCraftingRecipe {
    public BaubleRecolorRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        boolean baubleBl = false;
        boolean dyeBl = false;
        boolean bl = true;
        for (int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack input = recipeInputInventory.getStack(i);
            if (!input.isEmpty()) {
                if (input.isIn(ModTags.Items.BAUBLES_TAG) && !baubleBl) {
                    baubleBl = true;
                } else if (input.isIn(ConventionalItemTags.DYES) && !dyeBl) {
                    dyeBl = true;
                } else {
                    bl = false;
                }
            }
        }
        return bl && baubleBl && dyeBl;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, RegistryWrapper.WrapperLookup registryLookup) {
        ItemStack baubleStack = ItemStack.EMPTY;
        ItemStack dyeStack = ItemStack.EMPTY;
        for (int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack input = recipeInputInventory.getStack(i);
            if (input.isIn(ModTags.Items.BAUBLES_TAG)) {
                baubleStack = input;
            } else if (input.getItem() instanceof DyeItem) {
                dyeStack = input;
            } else {

            }
        }

        ItemStack stack = BaubleBlockItem.getItemStack(((DyeItem) dyeStack.getItem()).getColor());
        stack.applyComponentsFrom(baubleStack.getComponents());
        return stack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BAUBLE_RECOLOR;
    }
}
