package powercyphe.festive_frenzy.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import powercyphe.festive_frenzy.mixin.accessor.ExplosionAccessor;
import powercyphe.festive_frenzy.particle.BaubleExplosionEmitterParticleEffect;
import powercyphe.festive_frenzy.particle.BaubleExplosionParticleEffect;
import powercyphe.festive_frenzy.world.BaubleExplosion;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @ModifyArg(method = "affectWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 0), index = 1)
    private BlockState festive_frenzy$baubleExplosion(BlockState state) {
        Explosion explosion = (Explosion) (Object) this;
        if (explosion instanceof BaubleExplosion baubleExplosion) {
            return baubleExplosion.explosionModification.getBlock().getDefaultState();
        }
        return state;
    }

    @WrapOperation(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean festive_frenzy$baubleExplosion(Entity entity, DamageSource source, float amount, Operation<Boolean> original) {
        Explosion explosion = (Explosion) (Object) this;
        if (explosion instanceof BaubleExplosion baubleExplosion) {
            baubleExplosion.explosionModification.affectEntity(
                    new Vec3d(((ExplosionAccessor) explosion).getX(), ((ExplosionAccessor) explosion).getY(), ((ExplosionAccessor) explosion).getZ()),
                    entity, ((ExplosionAccessor) explosion).getPower()
            );
        }
        return original.call(entity, source, amount);
    }
}
