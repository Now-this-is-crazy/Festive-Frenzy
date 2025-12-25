package powercyphe.festive_frenzy.common.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import powercyphe.festive_frenzy.common.registry.FFParticles;

public record BaubleExplosionParticleOption(int color, boolean isEmitter) implements ParticleOptions {
    public static final MapCodec<BaubleExplosionParticleOption> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(Codec.INT.optionalFieldOf("color", -1).forGetter(BaubleExplosionParticleOption::color),
            Codec.BOOL.optionalFieldOf("isEmitter", false).forGetter(BaubleExplosionParticleOption::isEmitter)).apply(instance, BaubleExplosionParticleOption::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BaubleExplosionParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, BaubleExplosionParticleOption::color, ByteBufCodecs.BOOL, BaubleExplosionParticleOption::isEmitter, BaubleExplosionParticleOption::new);

    @Override
    public ParticleType<?> getType() {
        return FFParticles.BAUBLE_EXPLOSION;
    }
}
