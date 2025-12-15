package powercyphe.festive_frenzy.mixin.sharpened_candy_cane;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFParticles;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyArg(method = "sweepAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", ordinal = 0), index = 0)
    private <T extends ParticleOptions> T festive_frenzy$candySweep(T particleOptions) {
        Player player = (Player) (Object) this;
        if (player.getMainHandItem().is(FFItems.SHARPENED_CANDY_CANE)) {
            return (T) FFParticles.CANDY_SWEEP;
        }
        return particleOptions;
    }
}
