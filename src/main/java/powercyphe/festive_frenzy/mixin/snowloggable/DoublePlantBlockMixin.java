package powercyphe.festive_frenzy.mixin.snowloggable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(DoublePlantBlock.class)
public class DoublePlantBlockMixin {

    @WrapOperation(method = "placeAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 1))
    private static boolean festive_frenzy$snowloggable(LevelAccessor instance, BlockPos blockPos, BlockState state, int i, Operation<Boolean> original) {
        if (state.getBlock() instanceof SnowLoggable) {
            return original.call(instance, blockPos, state.setValue(SnowLoggable.SNOW_LAYERS, 0), i);
        }
        return original.call(instance, blockPos, state, i);
    }
}
