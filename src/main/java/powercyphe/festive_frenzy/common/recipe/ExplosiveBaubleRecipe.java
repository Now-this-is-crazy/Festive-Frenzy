package powercyphe.festive_frenzy.common.recipe;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.common.item.BaubleBlockItem;
import powercyphe.festive_frenzy.common.registry.ModItems;
import powercyphe.festive_frenzy.common.registry.ModRecipes;
import powercyphe.festive_frenzy.common.registry.ModTags;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public class ExplosiveBaubleRecipe extends SpecialCraftingRecipe {
    public ExplosiveBaubleRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        boolean baubleBl = false;
        boolean gunpowderBl = false;
        boolean modificationBl = false;
        boolean bl = true;
        for (int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack input = recipeInputInventory.getStack(i);
            if (!input.isEmpty()) {
                if (input.isIn(ModTags.Items.BAUBLES_TAG) && !baubleBl && !input.contains(ModItems.Components.EXPLOSION_STRENGTH)) {
                    baubleBl = true;
                } else if (input.isOf(Items.GUNPOWDER)) {
                    gunpowderBl = true;
                } else if (BaubleExplosion.ExplosionModification.fromItemStack(input) != null && !modificationBl) {
                    modificationBl = true;
                } else {
                    bl = false;
                }
            }
        }
        return bl && baubleBl && gunpowderBl;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, RegistryWrapper.WrapperLookup registryLookup) {
        ItemStack baubleStack = ItemStack.EMPTY;
        BaubleExplosion.ExplosionModification modification = BaubleExplosion.ExplosionModification.NONE;
        int gunpowder = 0;
        for (int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack input = recipeInputInventory.getStack(i);
            if (input.isIn(ModTags.Items.BAUBLES_TAG)) {
                baubleStack = input.getItem().getDefaultStack();
            } else if (input.isOf(Items.GUNPOWDER)) {
                gunpowder++;
            } else if (BaubleExplosion.ExplosionModification.fromItemStack(input) != null) {
                modification = BaubleExplosion.ExplosionModification.fromItemStack(input);
            }
        }

        baubleStack.set(ModItems.Components.EXPLOSION_STRENGTH, gunpowder);
        baubleStack.set(ModItems.Components.EXPLOSION_MODIFICATION, modification);
        return baubleStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.EXPLOSIVE_BAUBLE;
    }
}
