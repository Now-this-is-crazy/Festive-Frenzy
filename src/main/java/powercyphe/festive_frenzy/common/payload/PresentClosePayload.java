package powercyphe.festive_frenzy.common.payload;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.block.PresentBlock;
import powercyphe.festive_frenzy.common.block.entity.PresentBlockEntity;
import powercyphe.festive_frenzy.common.screen.PresentScreenHandler;

public record PresentClosePayload(int entityId) implements CustomPayload {
    public static final CustomPayload.Id<PresentClosePayload> ID = new CustomPayload.Id<>(FestiveFrenzy.id("present_close"));
    public static final PacketCodec<RegistryByteBuf, PresentClosePayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, PresentClosePayload::entityId, PresentClosePayload::new);

    public PresentClosePayload(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<PresentClosePayload> {
        @Override
        public void receive(PresentClosePayload payload, ServerPlayNetworking.Context context) {
            ServerPlayerEntity serverPlayerEntity = context.player();
            World world = serverPlayerEntity.getWorld();
            if (serverPlayerEntity.currentScreenHandler instanceof PresentScreenHandler presentScreenHandler) {
                if (presentScreenHandler.inventory instanceof PresentBlockEntity entity) {
                    PresentBlock.closePresent(world, entity, world.getBlockState(entity.getPos()), entity.getPos(), serverPlayerEntity);
                }
            }
        }
    }
}
