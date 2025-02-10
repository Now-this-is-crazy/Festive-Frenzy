package powercyphe.festive_frenzy.mixin.sharped_candy_cane;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import powercyphe.festive_frenzy.registry.ModItems;
import powercyphe.festive_frenzy.registry.ModParticles;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @ModifyArg(method = "spawnSweepAttackParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I", ordinal = 0), index = 0)
    private <T extends ParticleEffect> T festive_frenzy$candySweep(T particle) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getMainHandStack().isOf(ModItems.SHARPENED_CANDY_CANE)) {
            return (T) ModParticles.CANDY_SWEEP;
        }
        return particle;
    }
}
