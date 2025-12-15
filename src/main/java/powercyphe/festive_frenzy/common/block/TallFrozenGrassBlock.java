package powercyphe.festive_frenzy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

public class TallFrozenGrassBlock extends DoublePlantBlock implements SnowLoggable {
    public static final IntegerProperty SNOW_LAYERS = SnowLoggable.SNOW_LAYERS;

    public TallFrozenGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SNOW_LAYERS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOW_LAYERS);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return super.getShape(state, blockGetter, blockPos, collisionContext);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();

        BlockPos blockPos = context.getClickedPos();
        BlockState state = level.getBlockState(blockPos);

        return super.getStateForPlacement(context).setValue(SNOW_LAYERS, state.is(Blocks.SNOW) ? state.getValue(SnowLayerBlock.LAYERS) : 0);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        BlockPos upperPos = blockPos.above();
        level.setBlock(upperPos, copyWaterloggedFrom(level, upperPos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(SNOW_LAYERS, 0)), 3);;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return super.mayPlaceOn(state, blockGetter, blockPos) || (state.is(BlockTags.SNOW) && state.isFaceSturdy(blockGetter, blockPos, Direction.UP, SupportType.FULL));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        super.randomTick(state, serverLevel, blockPos, randomSource);
        this.tryMelt(serverLevel, state, blockPos);

        BlockPos otherPos = state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER ? blockPos.above() : blockPos.below();
        BlockState otherState = serverLevel.getBlockState(otherPos);

        if (otherState.is(this)) {
            this.tryMelt(serverLevel, otherState, otherPos);
        }
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.getValue(SNOW_LAYERS) == 8 ? 0.2F : 1.0F;
    }

    @Override
    public boolean canBeSnowLogged(BlockGetter blockGetter, BlockState state, BlockPos blockPos) {
        boolean isUpper = state.getValue(HALF) == DoubleBlockHalf.UPPER;
        if (isUpper) {
            BlockPos belowPos = blockPos.below();
            BlockState belowState = blockGetter.getBlockState(belowPos);
            return belowState.getValueOrElse(SNOW_LAYERS, 0) == 8;
        }

        return SnowLoggable.super.canBeSnowLogged(blockGetter, state, blockPos);
    }
}
