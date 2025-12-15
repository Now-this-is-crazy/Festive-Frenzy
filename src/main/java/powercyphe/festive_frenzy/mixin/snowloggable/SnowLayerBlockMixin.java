package powercyphe.festive_frenzy.mixin.snowloggable;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin {

    @ModifyReturnValue(method = "canSurvive", at = @At(value = "RETURN", ordinal = 2))
    private boolean festive_frenzy$snowloggable(boolean original, BlockState state, LevelReader levelReader, BlockPos blockPos) {
        return original || (state.getBlock() instanceof SnowLoggable snowLoggable && snowLoggable.getSnowLayers(state) == 8);
    }
}
