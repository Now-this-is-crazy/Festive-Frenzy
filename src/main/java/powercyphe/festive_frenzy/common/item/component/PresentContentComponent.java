package powercyphe.festive_frenzy.common.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.List;

public class PresentContentComponent {
    public static final PresentContentComponent DEFAULT = new PresentContentComponent(List.of());
    public static final Codec<PresentContentComponent> CODEC = ItemStack.CODEC.listOf().xmap(PresentContentComponent::new, (component) -> {
        return component.stacks;
    });
    public static final PacketCodec<RegistryByteBuf, PresentContentComponent> PACKET_CODEC = ItemStack.PACKET_CODEC.collect(PacketCodecs.toList()).xmap(PresentContentComponent::new, (component) -> {
        return component.stacks;
    });

    final List<ItemStack> stacks;

    public PresentContentComponent(List<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public List<ItemStack> getStacks() {
        return this.stacks;
    }
}
