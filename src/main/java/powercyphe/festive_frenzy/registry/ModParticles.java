package powercyphe.festive_frenzy.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import powercyphe.festive_frenzy.FestiveFrenzy;

public class ModParticles {
    public static DefaultParticleType CANDY_SWEEP = FabricParticleTypes.simple();
    public static DefaultParticleType CANDY_CRIT = FabricParticleTypes.simple();

    public static DefaultParticleType FAIRY_SPARK = FabricParticleTypes.simple();

    public static DefaultParticleType FROSTFLAKE = FabricParticleTypes.simple();
    public static DefaultParticleType FROSTFLAKE_TRAIL = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("candy_sweep"), CANDY_SWEEP);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("candy_crit"), CANDY_CRIT);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("fairy_spark"), FAIRY_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("frostflake"), FROSTFLAKE);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("frostflake_trail"), FROSTFLAKE_TRAIL);
    }
}
