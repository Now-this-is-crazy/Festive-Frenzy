package powercyphe.festive_frenzy.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.SnowyBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(SnowyBlock.class)
public class SnowyBlockMixin {

    @ModifyExpressionValue(method = "getStateForNeighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/SnowyBlock;isSnow(Lnet/minecraft/block/BlockState;)Z"))
    private boolean festive_frenzy$snowBlocks(boolean original, BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return original && shouldHaveSnow(world, neighborState, neighborPos);
    }

    @ModifyExpressionValue(method = "getPlacementState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/SnowyBlock;isSnow(Lnet/minecraft/block/BlockState;)Z"))
    private boolean festive_frenzy$snowBlocks(boolean original, ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos().up();
        BlockState state = ctx.getWorld().getBlockState(blockPos);
        return original && shouldHaveSnow(ctx.getWorld(), state, blockPos);
    }

    @Unique
    private static boolean shouldHaveSnow(BlockView blockView, BlockState state, BlockPos blockPos) {
        return state.isSideSolid(blockView, blockPos, Direction.DOWN, SideShapeType.RIGID);
    }
}
