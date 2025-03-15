package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionEmitterParticleEffect;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionParticleEffect;

public class ModParticles {
    public static SimpleParticleType CANDY_SWEEP = FabricParticleTypes.simple();
    public static SimpleParticleType CANDY_CRIT = FabricParticleTypes.simple();

    public static SimpleParticleType FAIRY_SPARK = FabricParticleTypes.simple();

    public static SimpleParticleType FROSTFLAKE = FabricParticleTypes.simple();
    public static SimpleParticleType FROSTFLAKE_TRAIL = FabricParticleTypes.simple();

    public static ParticleType<BaubleExplosionParticleEffect> BAUBLE_EXPLOSION = FabricParticleTypes.complex(BaubleExplosionParticleEffect.CODEC, BaubleExplosionParticleEffect.PACKET_CODEC);
    public static ParticleType<BaubleExplosionEmitterParticleEffect> BAUBLE_EXPLOSION_EMITTER = FabricParticleTypes.complex(BaubleExplosionEmitterParticleEffect.CODEC, BaubleExplosionEmitterParticleEffect.PACKET_CODEC);

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("candy_sweep"), CANDY_SWEEP);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("candy_crit"), CANDY_CRIT);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("fairy_spark"), FAIRY_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("frostflake"), FROSTFLAKE);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("frostflake_trail"), FROSTFLAKE_TRAIL);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("bauble_explosion"), BAUBLE_EXPLOSION);
        Registry.register(Registries.PARTICLE_TYPE, FestiveFrenzy.id("bauble_explosion_emitter"), BAUBLE_EXPLOSION_EMITTER);
    }
}
