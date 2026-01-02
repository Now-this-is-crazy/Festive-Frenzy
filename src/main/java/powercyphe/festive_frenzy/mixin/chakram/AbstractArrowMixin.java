package powercyphe.festive_frenzy.mixin.chakram;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.entity.WreathChakramProjectileEntity;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;contains(Lnet/minecraft/world/phys/Vec3;)Z"))
    private boolean festive_frenzy$stopClipping(boolean original) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        return !(arrow instanceof WreathChakramProjectileEntity) && original;
    }
}
