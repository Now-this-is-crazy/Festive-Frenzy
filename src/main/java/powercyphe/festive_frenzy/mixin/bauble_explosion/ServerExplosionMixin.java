package powercyphe.festive_frenzy.mixin.bauble_explosion;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.List;

@Mixin(ServerExplosion.class)
public class ServerExplosionMixin {

    @WrapOperation(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerExplosion;interactWithBlocks(Ljava/util/List;)V"))
    private void festive_frenzy$blockHandler(ServerExplosion explosion, List<BlockPos> list, Operation<Void> original) {
        original.call(explosion, list);
        if (explosion instanceof BaubleExplosion baubleExplosion) {
            for (BlockPos blockPos : list) {
                baubleExplosion.getExplosionModification().affectBlock(baubleExplosion.level(), baubleExplosion, blockPos);
            }
        }
    }

    @WrapOperation(method = "hurtEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean festive_frenzy$entityHandler(Entity entity, ServerLevel serverLevel, DamageSource damageSource, float knockbackMultiplier, Operation<Boolean> original) {
        ServerExplosion explosion = (ServerExplosion) (Object) this;

        boolean bl = original.call(entity, serverLevel, damageSource, knockbackMultiplier);
        if (explosion instanceof BaubleExplosion baubleExplosion && entity instanceof LivingEntity livingEntity) {
            baubleExplosion.getExplosionModification().affectEntity(baubleExplosion.level(), baubleExplosion, livingEntity);
        }
        return bl;
    }

}
