package powercyphe.festive_frenzy.mixin.snowloggable.snowy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(SnowAndFreezeFeature.class)
public class SnowAndFreezeFeatureMixin {

    // IMPORTANT: FIX FROZEN GRASS GEN

    @WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;"))
    private BlockPos.MutableBlockPos festive_frenzy$snowloggable(BlockPos.MutableBlockPos instance, int x, int y, int z, Operation<BlockPos.MutableBlockPos> original, FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();

        BlockPos.MutableBlockPos blockPos = original.call(instance, x, y, z);
        BlockState state = level.getBlockState(blockPos);

        BlockPos belowPos = blockPos.below();
        BlockState belowState = level.getBlockState(belowPos);

        if (state.getBlock() instanceof SnowLoggable snowLoggable) {
            if (snowLoggable.isSnowLogged(state) && belowState.hasProperty(SnowyDirtBlock.SNOWY)) {
                level.setBlock(belowPos, belowState.setValue(SnowyDirtBlock.SNOWY, true), 2);
            }
        }

        return blockPos;
    }
}
