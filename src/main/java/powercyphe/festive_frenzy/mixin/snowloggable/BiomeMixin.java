package powercyphe.festive_frenzy.mixin.snowloggable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(Biome.class)
public class BiomeMixin {

    @WrapOperation(method = "shouldSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean festive_frenzy$snowloggable(BlockState state, Block block, Operation<Boolean> original, LevelReader level, BlockPos blockPos) {
        return original.call(state, block) || (state.getBlock() instanceof SnowLoggable snowLoggable && snowLoggable.canBeSnowLogged(level, state, blockPos));
    }
}
