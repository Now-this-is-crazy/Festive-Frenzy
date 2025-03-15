package powercyphe.festive_frenzy.common.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import powercyphe.festive_frenzy.common.registry.ModParticles;

public class BaubleExplosionEmitterParticleEffect implements ParticleEffect {
    public static final MapCodec<BaubleExplosionEmitterParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(ItemStack.CODEC.fieldOf("bauble").forGetter(BaubleExplosionEmitterParticleEffect::getBauble))
                .apply(instance, BaubleExplosionEmitterParticleEffect::new);
    });
    public static final PacketCodec<RegistryByteBuf, BaubleExplosionEmitterParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            ItemStack.PACKET_CODEC, BaubleExplosionEmitterParticleEffect::getBauble, BaubleExplosionEmitterParticleEffect::new
    );


    private final ItemStack bauble;

    public BaubleExplosionEmitterParticleEffect(ItemStack item) {
        this.bauble = item;
    }

    public ItemStack getBauble() {
        return this.bauble;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.BAUBLE_EXPLOSION_EMITTER;
    }
}
