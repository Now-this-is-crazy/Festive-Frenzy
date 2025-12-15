package powercyphe.festive_frenzy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFTags;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public class BaubleBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty IS_GLOWING = BooleanProperty.create("is_glowing");
    public static final EnumProperty<BaubleExplosion.ExplosionModification> EXPLOSION_MODIFICATION =
            EnumProperty.create("explosion_modification", BaubleExplosion.ExplosionModification.class);
    public static final IntegerProperty EXPLOSION_POWER = IntegerProperty.create("explosion_power", 0, 8);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final int explosionColor;

    public BaubleBlock(int explosionColor, Properties properties) {
        super(properties);
        this.explosionColor = explosionColor;
        this.registerDefaultState(this.defaultBlockState().setValue(IS_GLOWING, false)
                .setValue(EXPLOSION_MODIFICATION, BaubleExplosion.ExplosionModification.NONE)
                .setValue(EXPLOSION_POWER, 0).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, IS_GLOWING, EXPLOSION_MODIFICATION, EXPLOSION_POWER);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.or(Shapes.box(0.3, 0.35, 0.3, 0.7, 0.8, 0.7), Shapes.box(0.4, 0.8, 0.4, 0.6, 1, 0.6));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        ItemStack stack = player.getMainHandItem().isEmpty() ? player.getOffhandItem() : player.getMainHandItem();

        if (stack.is(Items.GLOW_INK_SAC)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(blockPos, state.setValue(IS_GLOWING, true));
            }

            level.playSound(player, blockPos, SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, level, blockPos, player, blockHitResult);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        return this.hasStableSupport(levelReader, state, blockPos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        return super.getStateForPlacement(context).setValue(WATERLOGGED, level.getFluidState(blockPos).is(Fluids.WATER));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
        super.neighborChanged(state, level, blockPos, block, orientation, bl);
        if (!this.hasStableSupport(level, state, blockPos)) {
            ThrownBaubleProjectileEntity.fromBlock(level, state, blockPos);
            level.destroyBlock(blockPos, false);
        }
    }

    @Override
    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return !state.getValue(WATERLOGGED);
    }

    public boolean hasStableSupport(BlockGetter blockGetter, BlockState state, BlockPos blockPos) {
        BlockPos supportingBlockPos = blockPos.above();
        BlockState supportingState = blockGetter.getBlockState(supportingBlockPos);

        return supportingState.isFaceSturdy(blockGetter, supportingBlockPos, Direction.DOWN, SupportType.FULL) ||
                supportingState.is(FFTags.Blocks.SUPPORTS_BAUBLES);
    }

    public ItemStack getBaubleStack(BlockState state) {
        ItemStack stack = this.asItem().getDefaultInstance();
        stack.set(FFItems.Components.EXPLOSIVE_BAUBLE_COMPONENT, new ExplosiveBaubleComponent(
                state.getValue(EXPLOSION_MODIFICATION), state.getValue(EXPLOSION_POWER)));

        return stack;
    }

    public int getExplosionColor() {
        return this.explosionColor;
    }

    public static ItemLike fromColor(DyeColor color) {
        return switch (color) {
            case LIGHT_GRAY -> FFBlocks.LIGHT_GRAY_BAUBLE;
            case GRAY -> FFBlocks.GRAY_BAUBLE;
            case BLACK -> FFBlocks.BLACK_BAUBLE;
            case BROWN -> FFBlocks.BROWN_BAUBLE;
            case RED -> FFBlocks.RED_BAUBLE;
            case ORANGE -> FFBlocks.ORANGE_BAUBLE;
            case YELLOW -> FFBlocks.YELLOW_BAUBLE;
            case LIME -> FFBlocks.LIME_BAUBLE;
            case GREEN -> FFBlocks.GREEN_BAUBLE;
            case CYAN -> FFBlocks.CYAN_BAUBLE;
            case LIGHT_BLUE -> FFBlocks.LIGHT_BLUE_BAUBLE;
            case BLUE -> FFBlocks.BLUE_BAUBLE;
            case PURPLE -> FFBlocks.PURPLE_BAUBLE;
            case MAGENTA -> FFBlocks.MAGENTA_BAUBLE;
            case PINK -> FFBlocks.PINK_BAUBLE;
            case null, default -> FFBlocks.WHITE_BAUBLE;
        };
    }
}
