package powercyphe.festive_frenzy.mixin.frostflake;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import powercyphe.festive_frenzy.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.registry.ModDamageTypes;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
    private void festive_frenzy$arrowFix(LivingEntity instance, int stuckArrowCount) {
        PersistentProjectileEntity projectile = (PersistentProjectileEntity) (Object) this;
        if ((projectile instanceof ArrowEntity)) {
            instance.setStuckArrowCount(stuckArrowCount);
        }
    }

    @ModifyExpressionValue(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSources;arrow(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/entity/damage/DamageSource;"))
    private DamageSource festive_frenzy$frostflake(DamageSource original) {
        PersistentProjectileEntity projectile = (PersistentProjectileEntity) (Object) this;
        if (projectile instanceof FrostflakeProjectileEntity) {
            return ModDamageTypes.create(projectile.getWorld(), ModDamageTypes.FROSTFLAKE, projectile, projectile.getOwner());
        }
        return original;
    }
}
