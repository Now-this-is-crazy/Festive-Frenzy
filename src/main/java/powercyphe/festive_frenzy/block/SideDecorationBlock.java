package powercyphe.festive_frenzy.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.registry.ModTags;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class SideDecorationBlock extends Block {
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().collect(Util.toMap());
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesByState;


    public SideDecorationBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
        this.shapesByState = Map.copyOf(this.stateManager.getStates().stream().collect(Collectors.toMap(Function.identity(), SideDecorationBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = VoxelShapes.empty();

        if (state.get(NORTH)) {
            voxelShape = VoxelShapes.union(voxelShape, SOUTH_SHAPE);
        }

        if (state.get(SOUTH)) {
            voxelShape = VoxelShapes.union(voxelShape, NORTH_SHAPE);
        }

        if (state.get(EAST)) {
            voxelShape = VoxelShapes.union(voxelShape, WEST_SHAPE);
        }

        if (state.get(WEST)) {
            voxelShape = VoxelShapes.union(voxelShape, EAST_SHAPE);
        }

        return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapesByState.get(state);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
        super.appendProperties(builder);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.getStack().isOf(state.getBlock().asItem())) return false;
        return this.canPlaceAt(state, context.getWorld(), context.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos blockPos, BlockPos neighborPos) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, blockPos, neighborPos);
        }
        return updateState(world, state, blockPos);
    }

    public BlockState updateState(WorldAccess world, BlockState state, BlockPos blockPos) {
        BlockState newState = state;
        for (Direction direction : FACING_PROPERTIES.keySet()) {
            if (direction.getAxis() != Direction.Axis.Y) {
                if (!isSideSolid(world, blockPos, direction)) {
                    newState = newState.with(FACING_PROPERTIES.get(direction.getOpposite()), false);
                }
            }
        }
        int sides = 0;
        for (Direction direction : FACING_PROPERTIES.keySet()) {
            if (direction.getAxis() != Direction.Axis.Y) {
                if (newState.get(FACING_PROPERTIES.get(direction))) {
                    sides++;
                }
            }
        }


        return sides > 0 ? state : Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos blockPos) {
        BlockState currentState = world.getBlockState(blockPos);
        if (!currentState.isOf(state.getBlock()) && !currentState.isAir() && !currentState.isReplaceable()) return false;

        boolean bl = false;
        for (Direction direction : FACING_PROPERTIES.keySet()) {
            if (direction.getAxis() != Direction.Axis.Y) {
                if (isSideSolid(world, blockPos, direction.getOpposite())) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction side = ctx.getSide();
        BlockState state = this.getDefaultState();
        BlockState currentState = world.getBlockState(blockPos);

        if (side.getAxis() != Direction.Axis.Y) {
            if (currentState.isOf(this.asBlock())) {
                if (canPlaceOnSide(world, blockPos, side)) {
                    return currentState.with(getFacingProperty(side.getOpposite()), true);
                }
                for (Direction direction : FACING_PROPERTIES.keySet()) {
                    if (direction.getAxis() != Direction.Axis.Y) {
                        if (canPlaceOnSide(world, blockPos, direction)) {
                            return currentState.with(getFacingProperty(direction.getOpposite()), true);
                        }
                    }
                }
            }
            if (canPlaceOnSide(world, blockPos, side)) {
                return state.with(getFacingProperty(side.getOpposite()), true);
            }
        }
        return null;
    }

    public boolean canPlaceOnSide(WorldView world, BlockPos blockPos, Direction direction) {
        BlockState currentState = world.getBlockState(blockPos);
        if (currentState.isOf(this.asBlock())) {
            if (!currentState.get(getFacingProperty(direction.getOpposite())) && isSideSolid(world, blockPos, direction)) {
                return true;
            }
        } else {
            return isSideSolid(world, blockPos, direction);
        }
        return false;
    }

    public boolean isSideSolid(WorldView world, BlockPos blockPos, Direction direction) {
        return world.getBlockState(blockPos.offset(direction.getOpposite())).isSideSolid(world, blockPos.offset(direction.getOpposite()), direction.getOpposite(), SideShapeType.FULL) || world.getBlockState(blockPos.offset(direction.getOpposite())).isIn(ModTags.SIDE_DECO_PLACEABLE);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->
                    state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90 ->
                    state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
            case CLOCKWISE_90 ->
                    state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            default -> state;
        };
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK -> state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    public static BooleanProperty getFacingProperty(Direction direction) {
        return FACING_PROPERTIES.get(direction);
    }
}
