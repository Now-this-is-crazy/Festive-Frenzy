package powercyphe.festive_frenzy.item.material;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import powercyphe.festive_frenzy.registry.ModItems;

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
        return 4;
    }

    @Override
    public int getMiningLevel() {
        return 3;
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
