package powercyphe.festive_frenzy.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.festive_frenzy.mixin.accessor.ExplosionAccessor;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.function.BiConsumer;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

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

    @WrapOperation(method = "affectWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;onExploded(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/explosion/Explosion;Ljava/util/function/BiConsumer;)V"))
    private void festive_frenzy$baubleExplosion(BlockState instance, World world, BlockPos blockPos, Explosion explosion, BiConsumer<ItemStack, BlockPos> biConsumer, Operation<Void> original) {
        original.call(instance, world, blockPos, explosion, biConsumer);

        if (explosion instanceof BaubleExplosion baubleExplosion) {
            Vec3d pos = new Vec3d(((ExplosionAccessor) explosion).getX(), ((ExplosionAccessor) explosion).getY(), ((ExplosionAccessor) explosion).getZ());
            baubleExplosion.explosionModification.affectBlock(pos, world, blockPos, baubleExplosion.getPower());
        }
    }
}
