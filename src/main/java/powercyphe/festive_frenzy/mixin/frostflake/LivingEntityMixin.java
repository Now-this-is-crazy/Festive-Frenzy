package powercyphe.festive_frenzy.mixin.frostflake;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.registry.ModDamageTypes;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyExpressionValue(method = "playHurtSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHurtSound(Lnet/minecraft/entity/damage/DamageSource;)Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent festive_frenzy$frostflake_playHurtSound(SoundEvent original, DamageSource source) {
        if (source.isOf(ModDamageTypes.FROSTFLAKE)) {
            return null;
        }
        return original;
    }

    @ModifyExpressionValue(method = "onDamaged", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHurtSound(Lnet/minecraft/entity/damage/DamageSource;)Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent festive_frenzy$frostflake_onDamaged(SoundEvent original, DamageSource source) {
        if (source.isOf(ModDamageTypes.FROSTFLAKE)) {
            return null;
        }
        return original;
    }
}
