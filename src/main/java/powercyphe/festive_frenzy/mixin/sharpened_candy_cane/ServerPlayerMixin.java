package powercyphe.festive_frenzy.mixin.sharpened_candy_cane;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.payload.EmitterParticlePayload;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFParticles;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @WrapOperation(method = "crit", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;sendToTrackingPlayersAndSelf(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/Packet;)V"))
    private void festive_frenzy$critEmitterPayload(ServerChunkCache instance, Entity receiver, Packet<?> packet, Operation<Void> original, Entity entity) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (serverPlayer.getMainHandItem().is(FFItems.SHARPENED_CANDY_CANE)) {
            EmitterParticlePayload.send(serverPlayer, entity, (ParticleOptions) FFParticles.CANDY_CRIT);
            return;
        }

        original.call(instance, entity, packet);
    }

    @WrapOperation(method = "magicCrit", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;sendToTrackingPlayersAndSelf(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/Packet;)V"))
    private void festive_frenzy$magicCritEmitterPayload(ServerChunkCache instance, Entity receiver, Packet<?> packet, Operation<Void> original, Entity entity) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (serverPlayer.getMainHandItem().is(FFItems.SHARPENED_CANDY_CANE)) {
            EmitterParticlePayload.send(serverPlayer, entity, (ParticleOptions) FFParticles.FAIRY_SPARK);
            return;
        }

        original.call(instance, entity, packet);
    }
}
