package powercyphe.festive_frenzy.common.payload;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.block.PresentBlock;
import powercyphe.festive_frenzy.common.block.entity.PresentBlockEntity;
import powercyphe.festive_frenzy.common.menu.PresentMenu;

public record PresentClosePayload() implements CustomPacketPayload {
    public static final Type<PresentClosePayload> TYPE = new Type<>(FestiveFrenzy.id("present_close"));
    public static final StreamCodec<FriendlyByteBuf, PresentClosePayload> CODEC = StreamCodec.unit(new PresentClosePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<PresentClosePayload> {

        @Override
        public void receive(PresentClosePayload payload, ServerPlayNetworking.Context context) {
            ServerPlayer serverPlayer = context.player();
            Level level = serverPlayer.level();

            if (serverPlayer.containerMenu instanceof PresentMenu presentMenu &&
                    presentMenu.getContainer() instanceof PresentBlockEntity entity) {
                BlockState state = entity.getBlockState();
                BlockPos blockPos = entity.getBlockPos();

                if (state.getBlock() instanceof PresentBlock presentBlock) {
                    presentBlock.pickupPresent(level, state, blockPos, serverPlayer);
                }
            }
        }
    }
}
