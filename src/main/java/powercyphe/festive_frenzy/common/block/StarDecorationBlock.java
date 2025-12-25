package powercyphe.festive_frenzy.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.block.entity.StarDecorationBlockEntity;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFParticles;
import powercyphe.festive_frenzy.common.registry.FFTags;

import java.util.List;

public class StarDecorationBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<StarDecorationBlock> CODEC = simpleCodec(StarDecorationBlock::new);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StarDecorationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource random) {
        if (level.getBlockEntity(blockPos) instanceof StarDecorationBlockEntity starDecoration) {
            List<BlockPos> particlePoses = starDecoration.getParticlePoses();

            int particles = random.nextInt(1, Math.max(2, particlePoses.size() / 4));
            for (int i = 0; i < particles; i++) {
                if (particlePoses.isEmpty()) {
                    break;
                } else {
                    BlockPos parPos = particlePoses.remove((int) (random.nextFloat() * particlePoses.size()));

                    Vec3 pos = parPos.getBottomCenter().add(
                            Math.max(0.5F, random.nextFloat()) * 0.7 * (random.nextBoolean() ? 1 : -1),
                            Math.max(0.5F, random.nextFloat()) * 0.9,
                            Math.max(0.5F, random.nextFloat()) * 0.7 * (random.nextBoolean() ? 1 : -1)
                    );
                    level.addParticle((ParticleOptions) FFParticles.GOLDEN_SPARKLE, pos.x(), pos.y(), pos.z(), 0, 0, 0);
                }
            }
        }

        Vec3 pos = blockPos.getBottomCenter().add(
                Math.max(0.5F, random.nextFloat()) * 0.7 * (random.nextBoolean() ? 1 : -1),
                Math.max(0.5F, random.nextFloat()) * 0.9,
                Math.max(0.5F, random.nextFloat()) * 0.7 * (random.nextBoolean() ? 1 : -1)
        );
        level.addParticle((ParticleOptions) FFParticles.GOLDEN_SPARKLE, pos.x(), pos.y(), pos.z(), 0, 0, 0);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        return super.getStateForPlacement(context).setValue(WATERLOGGED, level.getFluidState(blockPos).is(Fluids.WATER));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        BlockPos belowPos = blockPos.below();
        BlockState belowState = levelReader.getBlockState(belowPos);

        return belowState.isFaceSturdy(levelReader, belowPos, Direction.UP, SupportType.FULL)
                || belowState.is(FFTags.Blocks.SUPPORTS_STAR_DECORATION);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        if (!canSurvive(state, levelReader, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
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
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new StarDecorationBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, FFBlocks.Entities.STAR_DECORATION_BLOCK_ENTITY, StarDecorationBlockEntity::staticTick);
    }
}
