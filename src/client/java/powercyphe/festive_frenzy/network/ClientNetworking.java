package powercyphe.festive_frenzy.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import powercyphe.festive_frenzy.registry.ModNetworking;
import powercyphe.festive_frenzy.screen.PresentScreenHandler;
import powercyphe.festive_frenzy.util.PresentTypeAccessor;
import powercyphe.festive_frenzy.world.BaubleExplosion;

public class ClientNetworking {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.BAUBLE_EXPLOSION_PACKET, ((baubleExplosionS2CPacket, clientPlayerEntity, packetSender) -> {
            BaubleExplosion explosion = new BaubleExplosion(clientPlayerEntity.getWorld(), (Entity)null, baubleExplosionS2CPacket.getX(), baubleExplosionS2CPacket.getY(), baubleExplosionS2CPacket.getZ(), baubleExplosionS2CPacket.getRadius(), baubleExplosionS2CPacket.getAffectedBlocks(), baubleExplosionS2CPacket.getStack());
            explosion.affectWorld(true);
            clientPlayerEntity.setVelocity(clientPlayerEntity.getVelocity().add((double)baubleExplosionS2CPacket.getPlayerVelocityX(), (double)baubleExplosionS2CPacket.getPlayerVelocityY(), (double)baubleExplosionS2CPacket.getPlayerVelocityZ()));
        }));
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.PRESENT_TYPE_PACKET, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            ClientPlayerEntity clientPlayer = minecraftClient.player;
            if (clientPlayer != null) {
                ((PresentTypeAccessor) clientPlayer).festive_frenzy$setPresent(packetByteBuf.readString());
            }
        }));
    }

}
