package powercyphe.festive_frenzy.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

public class HollyBushBlock extends BushBlock implements BonemealableBlock, SnowLoggable {
    public static final MapCodec<HollyBushBlock> CODEC = simpleCodec(HollyBushBlock::new);

    public static final int AGE_MAX = 2;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final IntegerProperty SNOW_LAYERS = SnowLoggable.SNOW_LAYERS;

    public HollyBushBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(SNOW_LAYERS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, SNOW_LAYERS);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Block.box(2F, 0F, 2F, 14F, 13F, 14F);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.applyPlacementProperty(context, super.getStateForPlacement(context));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        int age = state.getValue(AGE);
        if (age == BlockStateProperties.MAX_AGE_2) {
            BlockState newState = state.setValue(AGE, 1);
            popResource(level, blockPos, FFItems.HOLLY.getDefaultInstance()
                    .copyWithCount(RandomSource.create().nextInt(2) + 1));

            level.setBlock(blockPos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, newState));

            level.playSound(null, blockPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,
                    1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, blockPos, player, blockHitResult);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        super.randomTick(state, serverLevel, blockPos, randomSource);
        this.tryMelt(serverLevel, state, blockPos);

        int age = state.getValue(AGE);
        if (age < AGE_MAX && randomSource.nextInt(5) == 0 && serverLevel.getRawBrightness(blockPos.above(), 0) >= 9) {
            BlockState blockState2 = state.setValue(AGE, age + 1);
            serverLevel.setBlock(blockPos, blockState2, 2);
            serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(blockState2));
        }
    }

    @Override
    protected float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.getValue(SNOW_LAYERS) == 8 ? 0.2F : 1.0F;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState state) {
        return state.getValue(AGE) < AGE_MAX;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        int age = Math.min(AGE_MAX, blockState.getValue(AGE) + 1);
        serverLevel.setBlock(blockPos, blockState.setValue(AGE, age), 2);
    }
}
