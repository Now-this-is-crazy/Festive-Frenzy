package powercyphe.festive_frenzy.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import powercyphe.festive_frenzy.common.registry.FFGamerules;

public interface SnowLoggable {
    IntegerProperty SNOW_LAYERS = IntegerProperty.create("snow_layers", 0, 8);

    default boolean isSnowLogged(BlockState state) {
        return this.getSnowLayers(state) > 0;
    }

    default boolean canBeSnowLogged(BlockGetter blockGetter, BlockState state, BlockPos blockPos) {
        return true;
    }

    default VoxelShape getSnowLayerShape(BlockState state, BlockPos blockPos) {
        return getShapeByLayers(state.getValue(SNOW_LAYERS));
    }

    default VoxelShape getShapeByLayers(int layers) {
        return layers <= 0 ? Shapes.empty() : Block.box(0, 0, 0, 16, 2 * layers, 16);
    }

    default int getSnowLayers(BlockState state) {
        return state.getValue(SNOW_LAYERS);
    }

    default BlockState applyPlacementProperty(BlockPlaceContext context, BlockState defaultState) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        BlockState state = level.getBlockState(blockPos);

        return defaultState == null ? null : defaultState.setValue(SNOW_LAYERS, state.is(Blocks.SNOW)
                ? state.getValue(SnowLayerBlock.LAYERS) : 0);
    }

    default void tryMelt(ServerLevel level, BlockState state, BlockPos blockPos) {
        int layers = this.getSnowLayers(state);

        if (this.canMelt(level, state, blockPos) && level.getBrightness(LightLayer.BLOCK, blockPos) > 11) {
            if (level.getGameRules().getBoolean(FFGamerules.LAYERED_MELTING_SNOW_GAMERULE) && layers > 1) {
                level.setBlock(blockPos, state.setValue(SNOW_LAYERS, layers - 1), 2);
                return;
            }
            level.setBlock(blockPos, state.setValue(SNOW_LAYERS, 0), 2);
        }
    }

    default boolean canMelt(LevelAccessor level, BlockState state, BlockPos blockPos) {
        return !hasSnowAbove(level, state, blockPos);
    }

    static boolean hasSnowAbove(LevelAccessor level, BlockState state, BlockPos blockPos) {
        BlockPos abovePos = blockPos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        return aboveState.is(Blocks.SNOW) || aboveState.is(Blocks.SNOW_BLOCK) ||
                (aboveState.getBlock() instanceof SnowLoggable snowLoggable && snowLoggable.isSnowLogged(aboveState));
    }
}
