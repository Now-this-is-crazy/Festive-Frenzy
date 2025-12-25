package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import powercyphe.festive_frenzy.common.registry.FFEntities;
import powercyphe.festive_frenzy.common.registry.FFTags;

import java.util.concurrent.CompletableFuture;

public class FFEntityTypeTagProvider extends FabricTagProvider<EntityType<?>> {
    public FFEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENTITY_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(FFTags.Entities.DETACHES_BAUBLES)
                .add(
                        EntityType.ARROW,
                        EntityType.SPECTRAL_ARROW,
                        EntityType.TRIDENT,
                        FFEntities.THROWN_BAUBLE_PROJECTILE
                );
    }
}
