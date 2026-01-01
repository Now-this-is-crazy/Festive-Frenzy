package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import powercyphe.festive_frenzy.common.registry.FFDamageTypes;

import java.util.concurrent.CompletableFuture;

public class FFDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public FFDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        builder(DamageTypeTags.IS_FREEZING)
                .addOptional(FFDamageTypes.FROSTFLAKE);
        builder(DamageTypeTags.IS_PROJECTILE)
                .addOptional(FFDamageTypes.BAUBLE)
                .addOptional(FFDamageTypes.FROSTFLAKE);

        builder(DamageTypeTags.NO_KNOCKBACK)
                .addOptional(FFDamageTypes.FROSTFLAKE);
        builder(DamageTypeTags.NO_IMPACT)
                .addOptional(FFDamageTypes.FROSTFLAKE);
        builder(DamageTypeTags.BYPASSES_COOLDOWN)
                .addOptional(FFDamageTypes.FROSTFLAKE);
    }
}
