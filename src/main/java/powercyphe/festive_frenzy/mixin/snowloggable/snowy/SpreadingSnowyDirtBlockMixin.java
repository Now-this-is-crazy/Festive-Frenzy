package powercyphe.festive_frenzy.mixin.snowloggable.snowy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

    @Inject(method = "canBeGrass", at = @At("HEAD"), cancellable = true)
    private static void festive_frenzy$canBeGrassWithSnowloggable(BlockState state, LevelReader level, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos upperPos = blockPos.above();
        BlockState upperState = level.getBlockState(upperPos);

        if (upperState.getBlock() instanceof SnowLoggable snowLoggable) {
            if (snowLoggable.getSnowLayers(upperState) > 1) {
                cir.setReturnValue(false);
            }
        }
    }

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1))
    private static boolean festive_frenzy$snowloggable(ServerLevel level, BlockPos blockPos, BlockState state, Operation<Boolean> original) {
        BlockPos upperPos = blockPos.above();
        BlockState upperState = level.getBlockState(upperPos);

        if (upperState.getBlock() instanceof SnowLoggable snowLoggable) {
            if (snowLoggable.isSnowLogged(upperState)) {
                return original.call(level, blockPos, state.setValue(SnowyDirtBlock.SNOWY, true));
            }
        }
        return original.call(level, blockPos, state);
    }
}
