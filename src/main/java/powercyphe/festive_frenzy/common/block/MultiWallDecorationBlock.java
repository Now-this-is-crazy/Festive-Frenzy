package powercyphe.festive_frenzy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.registry.FFTags;

import java.util.HashMap;

public class MultiWallDecorationBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public static final HashMap<Direction, BooleanProperty> DIRECTION_TO_PROPERTY = new HashMap<>();

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public MultiWallDecorationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(NORTH, false)
                .setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, SOUTH, EAST, WEST);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        for (Direction direction : DIRECTION_TO_PROPERTY.keySet()) {
            if (state.getValue(getFacingProperty(direction))) {
                shape = Shapes.or(shape, switch (direction) {
                    case NORTH -> Shapes.box(0, 0, 0, 1, 1, 0.1);
                    case SOUTH -> Shapes.box(0, 0, 0.9, 1, 1, 1);
                    case EAST -> Shapes.box(0.9, 0, 0, 1, 1, 1);
                    case WEST -> Shapes.box(0, 0, 0, 0.1, 1, 1);
                    case null, default -> Shapes.empty();
                });
            }
        }

        return shape.isEmpty() ? Shapes.block() : shape;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        for (Direction direction : DIRECTION_TO_PROPERTY.keySet()) {
            if (this.hasStableSupport(levelReader, state, blockPos, direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        return this.canAdd(level, state, blockPos) && stack.is(this.asItem());
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState state = level.getBlockState(blockPos);
        Direction direction = context.getHorizontalDirection();

        boolean waterlogged = level.getFluidState(blockPos).is(Fluids.WATER);

        if (this.canAdd(level, state, blockPos, direction)) {
            return state.setValue(getFacingProperty(direction), true);
        } else if (this.canAdd(level, state, blockPos)) {
            for (Direction dir : DIRECTION_TO_PROPERTY.keySet()) {
                if (this.canAdd(level, state, blockPos, dir)) {
                    return state.setValue(getFacingProperty(dir), true);
                }
            }
        }

        BlockState placementState = super.getStateForPlacement(context).setValue(WATERLOGGED, waterlogged);
        if (this.hasStableSupport(level, state, blockPos, direction)) {
            return placementState.setValue(getFacingProperty(direction), true);
        } else {
            for (Direction dir : DIRECTION_TO_PROPERTY.keySet()) {
                if (this.hasStableSupport(level, state, blockPos, dir)) {
                    return placementState.setValue(getFacingProperty(dir), true);
                }
            }
        }
        return placementState;
    }

    public boolean canAdd(BlockGetter blockGetter, BlockState state, BlockPos blockPos) {
        for (Direction direction : DIRECTION_TO_PROPERTY.keySet()) {
            if (this.canAdd(blockGetter, state, blockPos, direction)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAdd(BlockGetter blockGetter, BlockState state, BlockPos blockPos, Direction direction) {
        return state.is(this) && !state.getValue(getFacingProperty(direction)) && this.hasStableSupport(blockGetter, state, blockPos, direction);
    }

    public boolean hasStableSupport(BlockGetter blockGetter, BlockState state, BlockPos blockPos, Direction direction) {
        BlockPos supportingBlockPos = blockPos.relative(direction);
        BlockState supportingState = blockGetter.getBlockState(supportingBlockPos);

        return supportingState.isFaceSturdy(blockGetter, supportingBlockPos, direction.getOpposite(), SupportType.FULL) ||
                supportingState.is(FFTags.Blocks.SUPPORTS_MULTIFACE_DECORATION);
    }

    public BooleanProperty getFacingProperty(Direction direction) {
        return DIRECTION_TO_PROPERTY.getOrDefault(direction, NORTH);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        int stableFaces = 0;
        for (Direction dir : DIRECTION_TO_PROPERTY.keySet()) {
            if (state.getValue(getFacingProperty(dir)) && this.hasStableSupport(levelReader, state, blockPos, dir)) {
                stableFaces++;
            }
        }
        return stableFaces > 0 ? super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource)
                : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST)).setValue(SOUTH, state.getValue(EAST))
                    .setValue(EAST, state.getValue(NORTH)).setValue(WEST, state.getValue(SOUTH));
            case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST)).setValue(SOUTH, state.getValue(WEST))
                    .setValue(EAST, state.getValue(SOUTH)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH))
                    .setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            case null, default -> state;
        };
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case null, default -> state;
        };
    }

    static {
        DIRECTION_TO_PROPERTY.put(Direction.NORTH, NORTH);
        DIRECTION_TO_PROPERTY.put(Direction.SOUTH, SOUTH);
        DIRECTION_TO_PROPERTY.put(Direction.EAST, EAST);
        DIRECTION_TO_PROPERTY.put(Direction.WEST, WEST);
    }
}
