package powercyphe.festive_frenzy.common.registry;

import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.recipe.*;

public class ModRecipes {
    public static RecipeSerializer<ExplosiveBaubleRecipe> EXPLOSIVE_BAUBLE = register("crafting_special_explosive_bauble", new SpecialRecipeSerializer(ExplosiveBaubleRecipe::new));

    public static RecipeSerializer<PresentRecolorRecipe> PRESENT_RECOLOR = register("crafting_special_present_recolor", new SpecialRecipeSerializer(PresentRecolorRecipe::new));
    public static RecipeSerializer<PresentStonecuttingRecipe> PRESENT_STONECUTTING = register("present_stonecutting", new PresentStonecuttingRecipe.Serializer<>(PresentStonecuttingRecipe::new));

    public static RecipeSerializer<BaubleRecolorRecipe> BAUBLE_RECOLOR = register("crafting_special_bauble_recolor", new SpecialRecipeSerializer(BaubleRecolorRecipe::new));
    public static RecipeSerializer<BaubleStonecuttingRecipe> BAUBLE_STONECUTTING = register("bauble_stonecutting", new BaubleStonecuttingRecipe.Serializer<>(BaubleStonecuttingRecipe::new));

    public static void init() {}

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, FestiveFrenzy.id(id), serializer);
    }

}


