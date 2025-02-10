package powercyphe.festive_frenzy.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import powercyphe.festive_frenzy.registry.ModParticles;

import java.util.Locale;

public class BaubleExplosionEmitterParticleEffect implements ParticleEffect {
    public static final Codec<BaubleExplosionEmitterParticleEffect> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ItemStack.CODEC.fieldOf("bauble").forGetter((effect) -> {
            return effect.bauble;
        })).apply(instance, BaubleExplosionEmitterParticleEffect::new);
    });
    public static final Factory<BaubleExplosionEmitterParticleEffect> PARAMETERS_FACTORY = new Factory<BaubleExplosionEmitterParticleEffect>() {
        public BaubleExplosionEmitterParticleEffect read(ParticleType<BaubleExplosionEmitterParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            String[] itemId = stringReader.readString().split(":");
            ItemStack item = Registries.ITEM.get(Identifier.of(itemId[0], itemId[1])).getDefaultStack();
            return new BaubleExplosionEmitterParticleEffect(item);
        }

        public BaubleExplosionEmitterParticleEffect read(ParticleType<BaubleExplosionEmitterParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            ItemStack item = packetByteBuf.readItemStack();
            return new BaubleExplosionEmitterParticleEffect(item);
        }
    };
    private final ItemStack bauble;

    public BaubleExplosionEmitterParticleEffect(ItemStack item) {
        this.bauble = item;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeItemStack(this.bauble);
    }

    public ItemStack getBauble() {
        return this.bauble;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.BAUBLE_EXPLOSION_EMITTER;
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %d", Registries.PARTICLE_TYPE.getId(this.getType()), this.bauble.toString());
    }
}
