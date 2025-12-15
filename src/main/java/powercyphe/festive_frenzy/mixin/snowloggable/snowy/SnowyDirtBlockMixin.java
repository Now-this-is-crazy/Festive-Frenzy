package powercyphe.festive_frenzy.mixin.snowloggable.snowy;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(SnowyDirtBlock.class)
public class SnowyDirtBlockMixin {

    @ModifyReturnValue(method = "isSnowySetting", at = @At("RETURN"))
    private static boolean festive_frenzy$snowloggable(boolean original, BlockState state) {
        return original || (state.getBlock() instanceof SnowLoggable snowLoggable && snowLoggable.isSnowLogged(state));
    }
}
