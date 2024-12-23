package powercyphe.festive_frenzy.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import powercyphe.festive_frenzy.network.ModNetworking;
import powercyphe.festive_frenzy.registry.ModItems;

@Mixin(net.minecraft.server.network.ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @ModifyArg(method = "addCritParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;sendToNearbyPlayers(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/packet/Packet;)V", ordinal = 0), index = 1)
    private Packet<?> festive_frenzy$candyCrit(Packet<?> packet) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (packet instanceof EntityAnimationS2CPacket entityAnimationS2CPacket && player.getMainHandStack().isOf(ModItems.SHARPENED_CANDY_CANE)) {
            Entity entity = player.getServerWorld().getEntityById(entityAnimationS2CPacket.getId());
            if (entity != null) {
                return new EntityAnimationS2CPacket(entity, ModNetworking.CANDY_CRIT_ANIMATION_ID);
            }
        }
        return packet;
    }
}
