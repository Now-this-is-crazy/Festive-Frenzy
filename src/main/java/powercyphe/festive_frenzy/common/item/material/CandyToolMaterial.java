package powercyphe.festive_frenzy.common.item.material;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import powercyphe.festive_frenzy.common.registry.ModItems;

public class CandyToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 847;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 7.5f;
    }

    @Override
    public float getAttackDamage() {
        return 3;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
    }

    @Override
    public int getEnchantability() {
        return 17;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.RED_CANDY_CANE, ModItems.GREEN_CANDY_CANE);
    }
}
