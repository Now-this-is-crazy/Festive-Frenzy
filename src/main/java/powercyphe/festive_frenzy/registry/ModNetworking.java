package powercyphe.festive_frenzy.registry;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.block.PresentBlock;
import powercyphe.festive_frenzy.block.entity.PresentBlockEntity;
import powercyphe.festive_frenzy.network.BaubleExplosionS2CPacket;
import powercyphe.festive_frenzy.screen.PresentScreenHandler;

public class ModNetworking {
    public static PacketType<BaubleExplosionS2CPacket> BAUBLE_EXPLOSION_PACKET = PacketType.create(FestiveFrenzy.id("bauble_explode"), BaubleExplosionS2CPacket::new);
    public static Identifier PRESENT_TYPE_PACKET = FestiveFrenzy.id("present_type");
    public static Identifier PRESENT_CLOSE_PACKET = FestiveFrenzy.id("present_close");

    public static final int SPARK_CRIT_ANIMATION_ID = 23;
    public static final int ENCHANTED_CANDY_ANIMATION_ID = 24;

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(PRESENT_CLOSE_PACKET, ((minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            World world = serverPlayerEntity.getWorld();
            if (serverPlayerEntity.currentScreenHandler instanceof PresentScreenHandler presentScreenHandler) {
                if (presentScreenHandler.inventory instanceof PresentBlockEntity entity) {
                    PresentBlock.closePresent(world, entity, world.getBlockState(entity.getPos()), entity.getPos(), serverPlayerEntity);
                }
            }
        }));
    }
}
