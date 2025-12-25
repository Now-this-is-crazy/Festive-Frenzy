package powercyphe.festive_frenzy.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public record ExplosiveBaubleComponent(BaubleExplosion.ExplosionModification explosionModification, int explosionPower) {
    public static final ExplosiveBaubleComponent DEFAULT = new ExplosiveBaubleComponent(BaubleExplosion.ExplosionModification.NONE, 0);

    public static final Codec<ExplosiveBaubleComponent> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(BaubleExplosion.ExplosionModification.CODEC.optionalFieldOf("explosionModification", BaubleExplosion.ExplosionModification.NONE)
                    .forGetter(ExplosiveBaubleComponent::explosionModification), ExtraCodecs.POSITIVE_INT.optionalFieldOf("explosionPower", 0)
                    .forGetter(ExplosiveBaubleComponent::explosionPower)).apply(instance, ExplosiveBaubleComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ExplosiveBaubleComponent> STREAM_CODEC = StreamCodec.composite(
            BaubleExplosion.ExplosionModification.STREAM_CODEC, ExplosiveBaubleComponent::explosionModification,
            ByteBufCodecs.INT, ExplosiveBaubleComponent::explosionPower, ExplosiveBaubleComponent::new);


    public static ExplosiveBaubleComponent get(ItemStack stack) {
        return stack.getOrDefault(FFItems.Components.EXPLOSIVE_BAUBLE_COMPONENT, DEFAULT);
    }

}
