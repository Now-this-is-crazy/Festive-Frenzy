package powercyphe.festive_frenzy.common.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import powercyphe.festive_frenzy.common.registry.ModParticles;

public class BaubleExplosionParticleEffect implements ParticleEffect {
    public static final MapCodec<BaubleExplosionParticleEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(ItemStack.CODEC.fieldOf("bauble").forGetter(BaubleExplosionParticleEffect::getBauble)).apply(instance, BaubleExplosionParticleEffect::new);
    });
    public static final PacketCodec<RegistryByteBuf, BaubleExplosionParticleEffect> PACKET_CODEC = PacketCodec.tuple(
                    ItemStack.PACKET_CODEC, BaubleExplosionParticleEffect::getBauble, BaubleExplosionParticleEffect::new
    );

    private final ItemStack bauble;

    public BaubleExplosionParticleEffect(ItemStack item) {
        this.bauble = item;
    }

    public ItemStack getBauble() {
        return this.bauble;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.BAUBLE_EXPLOSION;
    }
}
