package powercyphe.festive_frenzy.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import powercyphe.festive_frenzy.common.block.entity.PresentBlockEntity;

import java.util.ArrayList;
import java.util.List;

public record PresentContentsComponent(List<ItemStack> stacks, boolean closed) {
    public static final PresentContentsComponent DEFAULT = new PresentContentsComponent(new ArrayList<>(), false);

    public static final Codec<PresentContentsComponent> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(ItemStack.OPTIONAL_CODEC.listOf().optionalFieldOf("stacks", new ArrayList<>())
                    .forGetter(PresentContentsComponent::stacks), Codec.BOOL.optionalFieldOf("closed", false)
                    .forGetter(PresentContentsComponent::closed)).apply(instance, PresentContentsComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PresentContentsComponent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_LIST_STREAM_CODEC, PresentContentsComponent::stacks,
            ByteBufCodecs.BOOL, PresentContentsComponent::closed, PresentContentsComponent::new);

    public static PresentContentsComponent fromBlock(PresentBlockEntity present, boolean closed) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : present.getItems()) {
            if (!stack.isEmpty()) {
                stacks.add(stack);
            }
        }

        return new PresentContentsComponent(stacks, closed);
    }
}
