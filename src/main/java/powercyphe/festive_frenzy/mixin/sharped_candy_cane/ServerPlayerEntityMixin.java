package powercyphe.festive_frenzy.mixin.sharped_candy_cane;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import powercyphe.festive_frenzy.common.registry.ModNetworking;
import powercyphe.festive_frenzy.common.registry.ModItems;

@Mixin(net.minecraft.server.network.ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @ModifyArg(method = "addCritParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;sendToNearbyPlayers(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/packet/Packet;)V", ordinal = 0), index = 1)
    private Packet<?> festive_frenzy$sparkCrit(Packet<?> packet) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (packet instanceof EntityAnimationS2CPacket entityAnimationS2CPacket && player.getMainHandStack().isOf(ModItems.SHARPENED_CANDY_CANE)) {
            Entity entity = player.getWorld().getEntityById(entityAnimationS2CPacket.getEntityId());
            if (entity != null) {
                return new EntityAnimationS2CPacket(entity, ModNetworking.SPARK_CRIT_ANIMATION_ID);
            }
        }
        return packet;
    }

    @ModifyArg(method = "addEnchantedHitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;sendToNearbyPlayers(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/packet/Packet;)V", ordinal = 0), index = 1)
    private Packet<?> festive_frenzy$enchantedCandy(Packet<?> packet) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (packet instanceof EntityAnimationS2CPacket entityAnimationS2CPacket && player.getMainHandStack().isOf(ModItems.SHARPENED_CANDY_CANE)) {
            Entity entity = player.getWorld().getEntityById(entityAnimationS2CPacket.getEntityId());
            if (entity != null) {
                return new EntityAnimationS2CPacket(entity, ModNetworking.ENCHANTED_CANDY_ANIMATION_ID);
            }
        }
        return packet;
    }
}
