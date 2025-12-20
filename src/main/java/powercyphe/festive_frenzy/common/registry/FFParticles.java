package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionParticleOption;

public class FFParticles {

    public static final ParticleType<SimpleParticleType> CANDY_CRIT = register("candy_crit", FabricParticleTypes.simple());
    public static final ParticleType<SimpleParticleType> CANDY_SWEEP = register("candy_sweep", FabricParticleTypes.simple());
    public static final ParticleType<SimpleParticleType> FAIRY_SPARK = register("fairy_spark", FabricParticleTypes.simple());

    public static final ParticleType<SimpleParticleType> FROSTBURN = register("frostburn", FabricParticleTypes.simple());
    public static final ParticleType<SimpleParticleType> FROSTFLAKE = register("frostflake", FabricParticleTypes.simple());
    public static final ParticleType<SimpleParticleType> FROSTFLAKE_TRAIL = register("frostflake_trail", FabricParticleTypes.simple());

    public static final ParticleType<SimpleParticleType> GOLDEN_SPARKLE = register("golden_sparkle", FabricParticleTypes.simple());

    public static final ParticleType<BaubleExplosionParticleOption> BAUBLE_EXPLOSION = register("bauble_explosion",
            FabricParticleTypes.complex(true, BaubleExplosionParticleOption.CODEC, BaubleExplosionParticleOption.STREAM_CODEC));

    public static void init() {}

    public static <T extends ParticleOptions> ParticleType<T> register(String id, ParticleType<T> particleType) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, FestiveFrenzy.id(id), particleType);
    }
}
