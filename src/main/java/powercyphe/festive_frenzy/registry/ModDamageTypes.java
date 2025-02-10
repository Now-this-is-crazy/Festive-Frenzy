package powercyphe.festive_frenzy.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.FestiveFrenzy;

public class ModDamageTypes {
    public static RegistryKey<DamageType> FROSTFLAKE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, FestiveFrenzy.id("frostflake"));

    public static void init() {

    }

    public static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), source, attacker);
    }
}
