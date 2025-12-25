package powercyphe.festive_frenzy.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.registry.FFTags;

public class WreathBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<WreathBlock> CODEC = simpleCodec(WreathBlock::new);

    public WreathBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> Block.box(0, 0, 14, 16, 16, 16);
            case WEST -> Block.box(0, 0, 0, 2, 16, 16);
            case EAST -> Block.box(14, 0, 0, 16, 16, 16);
            case null, default -> Block.box(0, 0, 0, 16, 16, 2);
        };
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (hasStableSupport(levelReader, state, blockPos, direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        return !this.hasStableSupport(levelReader, state, blockPos, state.getValue(FACING)) ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();

        BlockState state = super.getStateForPlacement(context);
        BlockPos blockPos = context.getClickedPos();

        Direction placeDirection = context.getClickedFace().getOpposite();

        if (placeDirection.getAxis() != Direction.Axis.Y && hasStableSupport(level, state, blockPos, placeDirection)) {
            return state != null ? state.setValue(FACING, placeDirection) : null;
        } else {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (hasStableSupport(level, state, blockPos, direction)) {
                    return state != null ? state.setValue(FACING, direction) : null;
                }
            }
        }
        return state;
    }

    public boolean hasStableSupport(BlockGetter blockGetter, BlockState state, BlockPos blockPos, Direction direction) {
        BlockPos supportingBlockPos = blockPos.relative(direction);
        BlockState supportingState = blockGetter.getBlockState(supportingBlockPos);

        return supportingState.isFaceSturdy(blockGetter, supportingBlockPos, direction.getOpposite(), SupportType.FULL) ||
                supportingState.is(FFTags.Blocks.SUPPORTS_WREATH);
    }
}
