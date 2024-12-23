package powercyphe.festive_frenzy.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
    private void festive_frenzy$arrowFix(LivingEntity instance, int stuckArrowCount) {
        PersistentProjectileEntity projectile = (PersistentProjectileEntity) (Object) this;
        if ((projectile instanceof ArrowEntity)) {
            instance.setStuckArrowCount(stuckArrowCount);
        }
    }
}
