package powercyphe.festive_frenzy.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.PositionSourceType;
import powercyphe.festive_frenzy.registry.ModParticles;

import java.util.Locale;

public class BaubleExplosionParticleEffect implements ParticleEffect {
    public static final Codec<BaubleExplosionParticleEffect> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ItemStack.CODEC.fieldOf("bauble").forGetter((effect) -> {
            return effect.bauble;
        })).apply(instance, BaubleExplosionParticleEffect::new);
    });
    public static final ParticleEffect.Factory<BaubleExplosionParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<BaubleExplosionParticleEffect>() {
        public BaubleExplosionParticleEffect read(ParticleType<BaubleExplosionParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            String[] itemId = stringReader.readString().split(":");
            ItemStack item = Registries.ITEM.get(Identifier.of(itemId[0], itemId[1])).getDefaultStack();
            return new BaubleExplosionParticleEffect(item);
        }

        public BaubleExplosionParticleEffect read(ParticleType<BaubleExplosionParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            ItemStack item = packetByteBuf.readItemStack();
            return new BaubleExplosionParticleEffect(item);
        }
    };
    private final ItemStack bauble;

    public BaubleExplosionParticleEffect(ItemStack item) {
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
        return ModParticles.BAUBLE_EXPLOSION;
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %d", Registries.PARTICLE_TYPE.getId(this.getType()), this.bauble.toString());
    }
}
