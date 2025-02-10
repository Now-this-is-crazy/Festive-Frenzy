package powercyphe.festive_frenzy.recipe;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.item.BaubleBlockItem;
import powercyphe.festive_frenzy.registry.ModRecipes;
import powercyphe.festive_frenzy.registry.ModTags;

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
                if (input.isIn(ModTags.Items.BAUBLES_TAG) && !baubleBl && !input.getOrCreateNbt().contains(BaubleBlockItem.EXPLOSION_STRENGTH_KEY)) {
                    baubleBl = true;
                } else if (input.isOf(Items.GUNPOWDER)) {
                    gunpowderBl = true;
                } else if (BaubleBlockItem.ExplosionModification.fromItemStack(input) != null && !modificationBl) {
                    modificationBl = true;
                } else {
                    bl = false;
                }
            }
        }
        return bl && baubleBl && gunpowderBl;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager registryManager) {
        ItemStack baubleStack = ItemStack.EMPTY;
        BaubleBlockItem.ExplosionModification modification = BaubleBlockItem.ExplosionModification.NONE;
        int gunpowder = 0;
        for (int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack input = recipeInputInventory.getStack(i);
            if (input.isIn(ModTags.Items.BAUBLES_TAG)) {
                baubleStack = input.getItem().getDefaultStack();
            } else if (input.isOf(Items.GUNPOWDER)) {
                gunpowder++;
            } else if (BaubleBlockItem.ExplosionModification.fromItemStack(input) != null) {
                modification = BaubleBlockItem.ExplosionModification.fromItemStack(input);
            }
        }

        baubleStack.getOrCreateNbt().putInt(BaubleBlockItem.EXPLOSION_STRENGTH_KEY, gunpowder);
        baubleStack.getOrCreateNbt().putString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY, modification.getName());
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
