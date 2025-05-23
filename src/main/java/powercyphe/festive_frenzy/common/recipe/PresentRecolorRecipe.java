package powercyphe.festive_frenzy.common.recipe;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.common.item.PresentBlockItem;
import powercyphe.festive_frenzy.common.registry.ModRecipes;
import powercyphe.festive_frenzy.common.registry.ModTags;

public class PresentRecolorRecipe extends SpecialCraftingRecipe {
    public PresentRecolorRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput recipeInputInventory, World world) {
        boolean presentBl = false;
        boolean dyeBl = false;
        boolean bl = true;
        for (int i = 0; i < recipeInputInventory.getSize(); ++i) {
            ItemStack input = recipeInputInventory.getStackInSlot(i);
            if (!input.isEmpty()) {
                if (input.isIn(ModTags.Items.PRESENTS_TAG) && !presentBl) {
                    presentBl = true;
                } else if (input.isIn(ConventionalItemTags.DYES) && !dyeBl) {
                    dyeBl = true;
                } else {
                    bl = false;
                }
            }
        }
        return bl && presentBl && dyeBl;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput recipeInputInventory, RegistryWrapper.WrapperLookup registryLookup) {
        ItemStack presentStack = ItemStack.EMPTY;
        ItemStack dyeStack = ItemStack.EMPTY;
        for (int i = 0; i < recipeInputInventory.getSize(); ++i) {
            ItemStack input = recipeInputInventory.getStackInSlot(i);
            if (input.isIn(ModTags.Items.PRESENTS_TAG)) {
                presentStack = input;
            } else if (input.getItem() instanceof DyeItem) {
                dyeStack = input;
            } else {

            }
        }

        ItemStack stack = PresentBlockItem.getItemStack(((DyeItem) dyeStack.getItem()).getColor());
        stack.applyComponentsFrom(presentStack.getComponents());
        return stack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.PRESENT_RECOLOR;
    }
}
