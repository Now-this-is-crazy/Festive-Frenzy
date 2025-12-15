package powercyphe.festive_frenzy.mixin.snowloggable.snowy;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

    @Definition(id = "is", method = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
    @Definition(id = "SNOW", field = "Lnet/minecraft/world/level/block/Blocks;SNOW:Lnet/minecraft/world/level/block/Block;")
    @Expression("?.is(SNOW)")
    @ModifyExpressionValue(method = "randomTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean festive_frenzy$snowloggable(boolean original, BlockState state, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        BlockPos upperPos = blockPos.above();
        BlockState upperState = serverLevel.getBlockState(upperPos);

        return original || (upperState.getBlock() instanceof SnowLoggable snowLoggable && snowLoggable.isSnowLogged(upperState));
    }
}
