package powercyphe.festive_frenzy.mixin.frostburn;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.registry.FFEffects;

@Mixin(Entity.class)
public class EntityMixin {

    @ModifyReturnValue(method = "isFullyFrozen", at = @At("RETURN"))
    private boolean festive_frenzy$frostburn(boolean original) {
        Entity entity = (Entity) (Object) this;

        return original || (entity instanceof LivingEntity livingEntity &&
                livingEntity.hasEffect(FFEffects.FROSTBURN));
    }
}
