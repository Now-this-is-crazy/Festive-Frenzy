package powercyphe.festive_frenzy.common.mob_effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import powercyphe.festive_frenzy.common.registry.FFEffects;

public class FrostburnEffect extends MobEffect {
    public FrostburnEffect(MobEffectCategory mobEffectCategory, int color, ParticleOptions particleOptions) {
        super(mobEffectCategory, color, particleOptions);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        this.tryRemoveFire(entity.getEffect(FFEffects.FROSTBURN), entity, amplifier);
    }

    @Override
    public void onMobHurt(ServerLevel serverLevel, LivingEntity entity, int amplifier, DamageSource source, float damage) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            this.tryRemoveFire(entity.getEffect(FFEffects.FROSTBURN), entity, amplifier);
        }
    }

    public void tryRemoveFire(MobEffectInstance instance, LivingEntity entity, int amplifier) {
        if (instance != null && entity.isOnFire()) {
            int effectTicks = instance.getDuration();
            int fireTicks = entity.getRemainingFireTicks();

            int remainingTicks = effectTicks - fireTicks;
            if (remainingTicks > 0) {
                entity.extinguishFire();
                entity.forceAddEffect(new MobEffectInstance(FFEffects.FROSTBURN, remainingTicks, amplifier), null);
            } else {
                entity.setRemainingFireTicks(-remainingTicks);
                entity.removeEffect(FFEffects.FROSTBURN);
            }
        }
    }
}
