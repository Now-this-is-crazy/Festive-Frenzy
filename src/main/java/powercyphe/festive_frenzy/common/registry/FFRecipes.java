package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.*;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.recipe.ExplosiveBaubleRecipe;
import powercyphe.festive_frenzy.common.recipe.TransmuteStonecutterRecipe;

public class FFRecipes {

    public static final RecipeSerializer<ExplosiveBaubleRecipe> EXPLOSIVE_BAUBLE_RECIPE = register("explosive_bauble",
            new CustomRecipe.Serializer<>(ExplosiveBaubleRecipe::new));
    public static final RecipeSerializer<StonecutterRecipe> TRANSMUTE_STONECUTTING_RECIPE = register("transmute_stonecutting",
            new SingleItemRecipe.Serializer<>(TransmuteStonecutterRecipe::new));

    public static void init() {}

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, FestiveFrenzy.id(id), serializer);
    }
}
