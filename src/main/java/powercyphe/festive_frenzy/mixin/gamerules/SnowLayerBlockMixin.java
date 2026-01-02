package powercyphe.festive_frenzy.mixin.gamerules;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.registry.FFGamerules;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin {

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    private boolean festive_frenzy$layeredMeltingSnow(ServerLevel serverLevel, BlockPos blockPos, boolean updateNearby, Operation<Boolean> original, BlockState state) {
        boolean layeredMeltingSnow = serverLevel.getGameRules().get(FFGamerules.LAYERED_MELTING_SNOW_GAMERULE);
        int layers = state.getValue(SnowLayerBlock.LAYERS);

        if (layeredMeltingSnow && layers > 1) {
            return serverLevel.setBlock(blockPos, state.setValue(SnowLayerBlock.LAYERS, layers-1), 2);
        }

        return original.call(serverLevel, blockPos, updateNearby);
    }
}
