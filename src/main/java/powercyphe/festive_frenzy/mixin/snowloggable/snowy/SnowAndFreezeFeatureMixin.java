package powercyphe.festive_frenzy.mixin.snowloggable.snowy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(SnowAndFreezeFeature.class)
public class SnowAndFreezeFeatureMixin {

    @WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 1))
    private boolean festive_frenzy$snowloggable(WorldGenLevel level, BlockPos blockPos, BlockState state, int i, Operation<Boolean> original) {
        BlockState currentState = level.getBlockState(blockPos);

        if (currentState.getBlock() instanceof SnowLoggable) {
            state = currentState.setValue(SnowLoggable.SNOW_LAYERS, 1);
        }

        return original.call(level, blockPos, state, i);
    }
}
