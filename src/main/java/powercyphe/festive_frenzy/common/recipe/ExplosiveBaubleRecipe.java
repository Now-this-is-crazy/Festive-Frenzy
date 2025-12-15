package powercyphe.festive_frenzy.common.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFRecipes;
import powercyphe.festive_frenzy.common.registry.FFTags;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public class ExplosiveBaubleRecipe extends CustomRecipe {
    public ExplosiveBaubleRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return FFRecipes.EXPLOSIVE_BAUBLE_RECIPE;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        for (ItemStack stack : input.items()) {
            if (!this.isValidRecipeItem(stack)) {
                return false;
            }
        }

        return !this.getBauble(input).isEmpty() && this.getExplosionPower(input) > 0
                && this.getExplosionModification(input) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
        ItemStack explosiveBauble = this.getBauble(input).copyWithCount(1);
        explosiveBauble.set(FFItems.Components.EXPLOSIVE_BAUBLE_COMPONENT, new ExplosiveBaubleComponent(
                this.getExplosionModification(input),
                this.getExplosionPower(input)
        ));

        return explosiveBauble;
    }

    private ItemStack getBauble(CraftingInput input) {
        ItemStack bauble = ItemStack.EMPTY;

        for (ItemStack stack : input.items()) {
            if (stack.is(FFTags.Items.BAUBLES)) {

                // If there are more than 2 baubles, return an empty stack
                if (bauble.isEmpty()) {
                    bauble = stack;
                } else {
                    return ItemStack.EMPTY;
                }
            }
        }

        return bauble;
    }

    private int getExplosionPower(CraftingInput input) {
        int explosionPower = 0;
        for (ItemStack stack : input.items()) {
            if (!stack.isEmpty() && stack.is(FFTags.Items.ITEMS_WITH_EXPLOSION_POWER)) {
                explosionPower++;
            }
        }

        return explosionPower;
    }

    @Nullable
    private BaubleExplosion.ExplosionModification getExplosionModification(CraftingInput input) {
        BaubleExplosion.ExplosionModification explosionModification = BaubleExplosion.ExplosionModification.NONE;
        for (ItemStack stack : input.items()) {

            for (BaubleExplosion.ExplosionModification mod : BaubleExplosion.ExplosionModification.values()) {
                if (!stack.isEmpty() && stack.is(mod.getRecipeItem().asItem())) {

                    // Return null if there are more than 2 explosion modification materials
                    if (explosionModification != BaubleExplosion.ExplosionModification.NONE) {
                        return null;
                    } else {
                        explosionModification = mod;
                    }
                }
            }
        }

        return explosionModification;
    }

    private boolean isValidRecipeItem(ItemStack stack) {
        return stack.is(FFTags.Items.BAUBLES) || stack.is(FFTags.Items.ITEMS_WITH_EXPLOSION_POWER) ||
                BaubleExplosion.ExplosionModification.fromItem(stack) != null;
    }
}
