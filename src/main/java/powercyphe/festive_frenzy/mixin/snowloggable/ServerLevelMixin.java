package powercyphe.festive_frenzy.mixin.snowloggable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @WrapOperation(method = "tickPrecipitation", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 2))
    private boolean festive_frenzy$snowloggable(ServerLevel serverLevel, BlockPos blockPos, BlockState snowState, Operation<Boolean> original) {
        BlockState state = serverLevel.getBlockState(blockPos);

        if (state.getBlock() instanceof SnowLoggable snowLoggable) {
            int layers = snowLoggable.getSnowLayers(state);
            int maxLayers = serverLevel.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);

            if (layers < Math.min(maxLayers, 8)) {
                return serverLevel.setBlockAndUpdate(blockPos, state.setValue(SnowLoggable.SNOW_LAYERS, layers+1));
            } else {
                return false;
            }
        }

        return original.call(serverLevel, blockPos, snowState);
    }
}
