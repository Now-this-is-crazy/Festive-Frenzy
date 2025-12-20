package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.block.FrozenGrassBlock;
import powercyphe.festive_frenzy.common.block.TallFrozenGrassBlock;

public class FFFeatures {

    public static class Placed {

        public static ResourceKey<PlacedFeature> PATCH_SHORT_FROZEN_GRASS = register("patch_short_frozen_grass");
        public static ResourceKey<PlacedFeature> PATCH_TALL_FROZEN_GRASS = register("patch_tall_frozen_grass");

        public static ResourceKey<PlacedFeature> PATCH_HOLLY_BUSH = register("patch_holly_bush");

        public static void init() {
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and(context -> context.getBiome().getBaseTemperature() <= 0),
                    GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_SHORT_FROZEN_GRASS);
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and(context -> context.getBiome().getBaseTemperature() <= 0),
                    GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_TALL_FROZEN_GRASS);

            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld().and(BiomeSelectors.tag(BiomeTags.IS_FOREST)),
                    GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_HOLLY_BUSH);
        }

        public static ResourceKey<PlacedFeature> register(String id) {
            return ResourceKey.create(Registries.PLACED_FEATURE, FestiveFrenzy.id(id));
        }
    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_FROZEN_GRASS = register("patch_short_frozen_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_FROZEN_GRASS = register("patch_tall_frozen_grass");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HOLLY__BUSH = register("patch_holly_bush");

    public static void init() {
        Placed.init();
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> register(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FestiveFrenzy.id(id));

    }
}
