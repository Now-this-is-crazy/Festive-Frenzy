package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.FestiveFrenzy;

public class FFDamageTypes {

    public static final ResourceKey<DamageType> BAUBLE = register("bauble");
    public static final ResourceKey<DamageType> FROSTFLAKE = register("frostflake");

    public static DamageSource createSource(RegistryAccess registryAccess, ResourceKey<DamageType> damageType, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(registryAccess.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(damageType), source, attacker);
    }

    public static void init() {}

    public static ResourceKey<DamageType> register(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, FestiveFrenzy.id(id));
    }
}
