package powercyphe.festive_frenzy.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.festive_frenzy.network.ModNetworking;
import powercyphe.festive_frenzy.registry.ModItems;
import powercyphe.festive_frenzy.registry.ModParticles;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin extends ClientCommonNetworkHandler implements TickablePacketListener, ClientPlayPacketListener {

    @Shadow private ClientWorld world;

    protected ClientPlayNetworkHandlerMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method = "onEntityAnimation", at = @At(value = "TAIL"))
    private void festive_frenzy$candyCrit(EntityAnimationS2CPacket packet, CallbackInfo ci) {
        Entity entity = this.world.getEntityById(packet.getId());
        if (packet.getAnimationId() == ModNetworking.CANDY_CRIT_ANIMATION_ID) {
            this.client.particleManager.addEmitter(entity, ModParticles.FAIRY_SPARK);
        }
    }
}
